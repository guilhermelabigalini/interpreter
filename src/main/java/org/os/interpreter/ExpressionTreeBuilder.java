package org.os.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.os.interpreter.exptree.AddExpr;
import org.os.interpreter.exptree.AssignExpr;
import org.os.interpreter.exptree.AssignIncExpr;
import org.os.interpreter.exptree.BiggerEqualExpr;
import org.os.interpreter.exptree.BiggerExpr;
import org.os.interpreter.exptree.BoolAndExpr;
import org.os.interpreter.exptree.BoolOrExpr;
import org.os.interpreter.exptree.BreakExpr;
import org.os.interpreter.exptree.ConstExpr;
import org.os.interpreter.exptree.ContinueExpr;
import org.os.interpreter.exptree.EvaluableExpr;
import org.os.interpreter.exptree.CustomIncVarible;
import org.os.interpreter.exptree.DivAssignExpr;
import org.os.interpreter.exptree.DivExpr;
import org.os.interpreter.exptree.DoWhileExpr;
import org.os.interpreter.exptree.EqualExpr;
import org.os.interpreter.exptree.Expr;
import org.os.interpreter.exptree.ForExpr;
import org.os.interpreter.exptree.FuncExpr;
import org.os.interpreter.exptree.IfExpr;
import org.os.interpreter.exptree.InstructLstExpr;
import org.os.interpreter.exptree.MinusAssignExpr;
import org.os.interpreter.exptree.ModAssignExpr;
import org.os.interpreter.exptree.ModExpr;
import org.os.interpreter.exptree.NegExpr;
import org.os.interpreter.exptree.NotEqualExpr;
import org.os.interpreter.exptree.NotExpr;
import org.os.interpreter.exptree.PlusAssignExpr;
import org.os.interpreter.exptree.PosDecVarible;
import org.os.interpreter.exptree.PosIncVarible;
import org.os.interpreter.exptree.PreDecVarible;
import org.os.interpreter.exptree.PreIncVarible;
import org.os.interpreter.exptree.ProcExpr;
import org.os.interpreter.exptree.ReturnExpr;
import org.os.interpreter.exptree.SmallerEqualExpr;
import org.os.interpreter.exptree.SmallerExpr;
import org.os.interpreter.exptree.SubExpr;
import org.os.interpreter.exptree.SwitchExpr;
import org.os.interpreter.exptree.TimesAssignExpr;
import org.os.interpreter.exptree.TimesExpr;
import org.os.interpreter.exptree.TwoOpsExpr;
import org.os.interpreter.exptree.UserFuncCaller;
import org.os.interpreter.exptree.UserFuncTemp;
import org.os.interpreter.exptree.VaribleExpr;
import org.os.interpreter.exptree.WhileExpr;
import org.os.interpreter.token.Bookmark;
import org.os.interpreter.token.StreamToken;
import org.os.interpreter.token.Token;
import org.os.interpreter.token.Tokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author guilherme
 */
public class ExpressionTreeBuilder {

    private static final int MIN_OP_LEVEL = 1;

    private final Tokenizer FTokenizer;
    int FLastExprLine;
    boolean FReadingFunction;
    private HashMap<String, UserFuncTemp> FUserFunctions;
    private ProcExpr procExpr;

    private StreamToken FLastTkn;
    private final List<Error> errors;

    private int loopCount;
    private int caseCount;
    private UserFuncTemp FCurUserFunc;

    public ExpressionTreeBuilder(String source) {
        this.errors = new ArrayList<>();
        this.FTokenizer = new Tokenizer(source);
        this.FLastTkn = new StreamToken(Token.ttUnknown);
    }

    public ProcExpr compile() throws ParseException {

        FUserFunctions = new HashMap<>();
        procExpr = new ProcExpr();
        loopCount = 0;
        caseCount = 0;
        FReadingFunction = false;

        if (!ReadProc(procExpr)) {
            throw new ParseException(this.errors);
        }

        return procExpr;
    }

    protected void logError(String msg) {
        errors.add(new Error(FTokenizer.getCurrentLine(), msg));
    }

    private boolean ReadProc(InstructLstExpr Expr) {
        boolean b = false;
        while (FLastTkn.getToken() != Token.ttEof && (b = ReadStmnt(Expr)) == true)
            ;
        return b;
    }

    private boolean ReadStmnt(InstructLstExpr Expr) {
        StreamToken tkn;
        boolean tmpR;

        tkn = FTokenizer.GetNextToken();
        FLastExprLine = FTokenizer.getCurrentLine();
        FLastTkn = tkn;

        switch (tkn.getToken()) {
            case ttVar:
                return ReadVar(Expr, tkn);
            case ttName:
                return ReadExpr(tkn, Expr);
            case ttWhile:
                loopCount++;
                tmpR = ReadWhile(Expr);
                loopCount--;
                return tmpR;
            case ttDo:
                loopCount++;
                tmpR = ReadDoWhile(Expr);
                loopCount--;
                return tmpR;
            case ttFor:
                loopCount++;
                tmpR = ReadFor(Expr);
                loopCount--;
                return tmpR;
            case ttIf:
                return ReadIf(Expr);
            case ttSwitch:
                caseCount++;
                tmpR = ReadSwitch(Expr);
                caseCount--;
                return tmpR;
            /*
            case ttTry:
                return ReadTry(Expr);
            case ttThrow:
                return ReadThrow(Expr);*/
            case ttFunction:
                if (FReadingFunction) {
                    logError(Error.EFUNCINFUNC);
                    return false;
                }
                FReadingFunction = true;
                tmpR = ReadFunction(Expr);
                FReadingFunction = false;
                return tmpR;
            case ttReturn:
                return ReadReturn(Expr);
            case ttBreak:
                if (loopCount <= 0 && caseCount <= 0) {
                    logError(Error.EINVBREAKPOS);
                    return false;
                }
                if (FTokenizer.GetNextToken().getToken() != Token.ttSemicolon) {
                    logError(Error.ESEMICOLON);
                    return false;
                }
                Expr.AddItem(new BreakExpr(Expr));
                return true;
            case ttContinue:
                if (loopCount <= 0 && caseCount <= 0) {
                    logError(Error.EINVCONTPOS);
                    return false;
                }
                if (FTokenizer.GetNextToken().getToken() != Token.ttSemicolon) {
                    logError(Error.ESEMICOLON);
                    return false;
                }
                Expr.AddItem(new ContinueExpr(Expr));
                return true;/*
            case ttData:
                // TODO: Expr.AddItem(new COutputDataExpr(Expr, (char *) tkn.Data));
                return true;
            case ttExpr:
                return ReadOutExpr(Expr);*/
            case ttSemicolon:
            case ttEof:
                return true;
            default:
                logError(Error.ESTMNT);
                return false;
        }
    }

    private boolean ReadSwitch(InstructLstExpr Expr) {
        SwitchExpr switchExpr;
        Reference<EvaluableExpr> EvalExpr = new Reference<>();
        Reference<EvaluableExpr> CaseItem = new Reference<>();
        SwitchExpr.SwitchItem switchItem;
        StreamToken tkn;

        tkn = FTokenizer.GetNextToken();

        if (tkn.getToken() != Token.ttBLeft) {
            //switch nao seguido de parentes, gerar erro
            logError(Error.EBLEFTSWITCH);
            return false;
        }

        if (!LeComNivels(Expr, EvalExpr, ExpressionStep.esSubExpr)) //erro ao ler condicao, erro ja foi gerado
        {
            return false;
        }

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttBegin) {
            logError(Error.ESWITCHBGN);
            return false;
        }

        tkn = FTokenizer.GetNextToken();

        switchExpr = new SwitchExpr(Expr, EvalExpr.getValue());

        while (tkn.getToken() != Token.ttEnd) {
            if (tkn.getToken() == Token.ttCase) {
                switchItem = new SwitchExpr.SwitchItem(switchExpr);
                do {
                    CaseItem.setValue(null);
                    if (!LeComNivels(Expr, CaseItem, ExpressionStep.esCase)) //erro ao ler condicao, erro ja foi gerado
                    {
                        return false;
                    }
                    switchItem.getConditions().add(CaseItem.getValue());
                } while (FLastTkn.getToken() == Token.ttComma);

                if (FLastTkn.getToken() != Token.ttTwoPoints) {
                    logError(Error.E2POINTS);
                    return false;
                }

                if (!ReadBlock(switchItem.getProcExpr(), false)) {
                    return false;
                }
                switchExpr.getCases().add(switchItem);

            } else if (tkn.getToken() == Token.ttDefault) {
                if (switchExpr.isDefaultBlockDefined()) {
                    logError(Error.EDEFEXISTS);
                    return false;
                }
                tkn = FTokenizer.GetNextToken();
                if (tkn.getToken() != Token.ttTwoPoints) {
                    logError(Error.E2POINTS);
                    return false;
                }
                if (!ReadBlock(switchExpr.getDefaultProc(), false)) {
                    return false;
                }
            } else {
                logError(Error.ECASESTMNT);
                return false;
            }
            tkn = FTokenizer.GetNextToken();
        }
        Expr.AddItem(switchExpr);
        return true;
    }

    private boolean ReadVar(InstructLstExpr Expr, StreamToken CurTkn) {
        StreamToken nametkn;
        Bookmark Book;

        StreamToken tkn = FTokenizer.GetNextToken();
        do {
            if (tkn.getToken() == Token.ttName) {
                if (Expr.ExitsVar((String) tkn.getData(), false)) {
                    //the varible alrealy exists
                    logError(Error.EVAREXISTS);
                    return false;
                }
                //todo -carray: checar se o variavel é uma array
                nametkn = tkn;
                Expr.AddVar((String) tkn.getData());
            } else {
                //nome nao encontrado p/variavel
                logError(Error.EVARNAMENTFND);
                return false;
            }

            Book = FTokenizer.GetBookmark();
            tkn = FTokenizer.GetNextToken();

            //the user is initializing the varible in declaration
            if (tkn.getToken() == Token.ttEqual) {
                Reference<Expr> AssignExpr = new Reference<>();
                /* TODO 3 -cbook: qnd da bookmark, o token antigo tem que ser desalocado
               se o token, fosse ttName, um char * estaria alocado */
                FTokenizer.GotoBookmark(Book);
                if (!ReadExpr(Expr, nametkn, AssignExpr, ExpressionStep.esInit)) {
                    return false;
                }
                Expr.AddInitItem(AssignExpr.getValue());
                tkn = FLastTkn;
            }

            switch (tkn.getToken()) {
                case ttComma:
                    tkn = FTokenizer.GetNextToken();
                    if (tkn.getToken() != Token.ttName) {
                        //nome nao encontrado p/variavel
                        logError(Error.EVARNAMENTFND);
                        return false;
                    }
                    break;
                case ttSemicolon:
                    return true;
                default:
                    //missing ";"
                    logError(Error.ESEMICOLON);
                    return false;
            }
        } while (true);
    }

    private boolean ReadExpr(StreamToken CurTkn, InstructLstExpr Expr) {
        Reference<Expr> Result = new Reference<>();

        if (ReadExpr(Expr, CurTkn, Result, ExpressionStep.esInit)) {
            Expr.AddItem(Result.getValue());
            return true;
        }

        return false;
    }

    private boolean ReadExpr(InstructLstExpr Expr, StreamToken CurTkn,
            Reference<Expr> Result, ExpressionStep ExprStep) {

        String Name;
        EvaluableExpr EvalExpr;
        AssignExpr AssignExpr;
        CustomIncVarible IncExpr;

        Name = (String) CurTkn.getData();
        StreamToken tkn = FTokenizer.GetNextToken();

        //todo -cArray: checar se o simbolo é [, assim a variavel é um array
        switch (tkn.getToken()) {
            case ttEqual:
            case ttPlusEqual:
            case ttMinusEqual:
            case ttTimesEqual:
            case ttDivEqual:
            case ttModEqual:
                //expressao do tipo "varName = ..."
                if (Expr.ExitsVar(Name, true)) {
                    //a variavel foi encontrada, .·. devemos processar a expressao
                    Reference<EvaluableExpr> ref = new Reference<>();

                    if (!LeComNivels(Expr, ref, ExprStep)) {
                        return false;
                    }

                    EvalExpr = ref.getValue();

                    if (tkn.getToken() == Token.ttEqual) {
                        AssignExpr = new AssignExpr(Expr, EvalExpr, Name);
                    } else if (tkn.getToken() == Token.ttPlusEqual) {
                        AssignExpr = new PlusAssignExpr(Expr, EvalExpr, Name);
                    } else if (tkn.getToken() == Token.ttMinusEqual) {
                        AssignExpr = new MinusAssignExpr(Expr, EvalExpr, Name);
                    } else if (tkn.getToken() == Token.ttTimesEqual) {
                        AssignExpr = new TimesAssignExpr(Expr, EvalExpr, Name);
                    } else if (tkn.getToken() == Token.ttDivEqual) {
                        AssignExpr = new DivAssignExpr(Expr, EvalExpr, Name);
                    } else {
                        AssignExpr = new ModAssignExpr(Expr, EvalExpr, Name);
                    }

                    Result.setValue(AssignExpr);

                    return true;
                } else {
                    //variável nao encontrada, gerar erro
                    logError(Error.VARNOTFOUND);
                    return false;
                }
            case ttPlusPlus:
            case ttMinusMinus:

                if (!Expr.ExitsVar(Name, true)) {
                    logError(Error.VARNOTFOUND);
                    return false;
                }

                EvalExpr = new VaribleExpr(Name, Expr);

                if (tkn.getToken() == Token.ttPlusPlus) {
                    IncExpr = new PosIncVarible(EvalExpr);
                } else {
                    IncExpr = new PosDecVarible(EvalExpr);
                }

                tkn = FTokenizer.GetNextToken();

                if (tkn.getToken() == Token.ttBRight && !(ExprStep == ExpressionStep.esSubExpr || ExprStep == ExpressionStep.esFunction)) {
                    logError(Error.EOPEXPBLRGHT);
                    return false;
                }
                if (tkn.getToken() == Token.ttComma && !(ExprStep == ExpressionStep.esFunction || ExprStep == ExpressionStep.esCase || ExprStep == ExpressionStep.esInit)) {
                    logError(Error.EOPEXPCOMMA);
                    return false;
                }
                if (tkn.getToken() == Token.ttTwoPoints && ExprStep != ExpressionStep.esCase) {
                    logError(Error.EOPEXP2PNTS);
                    return false;
                }

                Result.setValue(new AssignIncExpr(Expr, IncExpr));

                return true;
            case ttBLeft:
                /*if (FFunctions -> Find(AnsiString(Name), Index)) {

                    FuncExpr = new FuncExpr(Expr);
                    Result.setValue(FuncExpr);

                    FLastTkn = tkn;
                    if (!_ReadFuncParams(FuncExpr, Index)) {
                        return false;
                    }

                    tkn = FTokenizer.GetNextToken();
                    if (tkn.getToken() != Token.ttSemicolon) {
                        //error: falta ";"
                        logError(Error.ESEMICOLON);
                        return false;
                    }
                    return true;
                } else*/ if (FUserFunctions.containsKey(Name)) {

                    UserFuncTemp UserFuncTemp = FUserFunctions.get(Name);
                    UserFuncCaller UserFuncExpr = new UserFuncCaller(Expr, UserFuncTemp);

                    Result.setValue(UserFuncExpr);

                    if (UserFuncTemp.getTotalParameters() > 0) {
                        int pCount = 0;
                        Reference<EvaluableExpr> ref = new Reference<>();
                        while (FLastTkn.getToken() != Token.ttBRight && FLastTkn.getToken() != Token.ttEof) {
                            //we have a value parameter
                            if (!LeComNivels(Expr,  ref, ExpressionStep.esFunction)) {
                                return false;
                            }
                            UserFuncExpr.AddArg(ref.getValue());
                            pCount++;
                        }

                        if (pCount != UserFuncTemp.getTotalParameters()) {
                            logError(Error.ENMBROFPARAMS);
                            return false;
                        }
                    } else if (FTokenizer.GetNextToken().getToken() != Token.ttBRight) {
                        logError(Error.EFUNCBRIGHT);
                        return false;
                    }
                    tkn = FTokenizer.GetNextToken();
                    if (tkn.getToken() != Token.ttSemicolon) {
                        logError(Error.ESEMICOLON);
                        return false;
                    }
                    return true;
                } else {
                    //funcao nao encontrada, gerar erro
                    logError(Error.FUNCNOTFOUND + Name);
                    return false;
                }
            default:
                //nome precisa ser seguido de = ou (
                //para indicar chamada de fnc ou assiguinamento
                logError(Error.ESTMNT);
        }
        return false;
    }

    private boolean LeComNivels(InstructLstExpr Expr, Reference<EvaluableExpr> Result, ExpressionStep ExprStep) {

        TwoOpsExpr Expr2Ops;
        StreamToken tkn;
        List<EvaluableExpr> ValuesLst = new ArrayList<>();
        List<Token> OpsLst = new ArrayList<>();
        EvaluableExpr x, y;
        int i, min;

        do {
            Reference<EvaluableExpr> refEvalExp = new Reference<>();

            if (!LeValor(Expr, refEvalExp)) {
                return false;
            }

            ValuesLst.add(refEvalExp.getValue());

            tkn = FTokenizer.GetNextToken();
            FLastTkn = tkn;
            if (tkn.getToken() == Token.ttBRight || tkn.getToken() == Token.ttSemicolon || tkn.getToken() == Token.ttComma
                    || tkn.getToken() == Token.ttTwoPoints) {
                if (tkn.getToken() == Token.ttBRight && !(ExprStep == ExpressionStep.esSubExpr || ExprStep == ExpressionStep.esFunction)) {
                    logError(Error.EOPEXPBLRGHT);
                    return false;
                }
                if (tkn.getToken() == Token.ttComma && !(ExprStep == ExpressionStep.esFunction || ExprStep == ExpressionStep.esCase || ExprStep == ExpressionStep.esInit)) {
                    logError(Error.EOPEXPCOMMA);
                    return false;
                }
                if (tkn.getToken() == Token.ttTwoPoints && ExprStep != ExpressionStep.esCase) {
                    logError(Error.EOPEXP2PNTS);
                    return false;
                }
                break;
            }
            if (!tkn.getToken().IsOperator()) {
                logError(Error.EERRHNDNGOP);
                return false;
            }
            OpsLst.add(tkn.getToken());
        } while (true);

        while (ValuesLst.size() > 1) {
            min = 0;
            for (i = 1; i < OpsLst.size(); i++) {

                if (MIN_OP_LEVEL == GetOperatorLevel(OpsLst.get(min))) {
                    break;
                }
                if (GetOperatorLevel(OpsLst.get(i)) < GetOperatorLevel(OpsLst.get(min))) {
                    min = i;
                }
            }

            x = (ValuesLst.get(min));
            y = (ValuesLst.get(min + 1));

            switch (OpsLst.get(min)) {
                case ttTimes:
                    Expr2Ops = new TimesExpr(x, y);
                    break;
                case ttDiv:
                    Expr2Ops = new DivExpr(x, y);
                    break;
                case ttMod:
                    Expr2Ops = new ModExpr(x, y);
                    break;
                case ttPlus:
                    Expr2Ops = new AddExpr(x, y);
                    break;
                case ttMinus:
                    Expr2Ops = new SubExpr(x, y);
                    break;
                case ttBigger:
                    Expr2Ops = new BiggerExpr(x, y);
                    break;
                case ttBEqual:
                    Expr2Ops = new BiggerEqualExpr(x, y);
                    break;
                case ttSmall:
                    Expr2Ops = new SmallerExpr(x, y);
                    break;
                case ttSEqual:
                    Expr2Ops = new SmallerEqualExpr(x, y);
                    break;
                case ttIsEqual:
                    Expr2Ops = new EqualExpr(x, y);
                    break;
                case ttNotEqual:
                    Expr2Ops = new NotEqualExpr(x, y);
                    break;
                case ttOr:
                    Expr2Ops = new BoolOrExpr(x, y);
                    break;
                case ttAnd:
                    Expr2Ops = new BoolAndExpr(x, y);
                    break;
                default:
                    Expr2Ops = null;
            }
            if (Expr2Ops == null) {
                logError(Error.EERRHNDNGOP);
                return false;
            }
            ValuesLst.remove(min + 1);
            OpsLst.remove(min);
            ValuesLst.set(min, Expr2Ops);
        }
        Result.setValue(ValuesLst.get(0));
        return true;
    }

    private boolean LeValor(InstructLstExpr Expr, Reference<EvaluableExpr> Result) {
        StreamToken tkn;
        EvaluableExpr OldR;
        ExpressionOp firstOp = ExpressionOp.opNone, secondOp = ExpressionOp.opNone;
        int i;

        Result.setValue(null);

        tkn = FTokenizer.GetNextToken();
        switch (tkn.getToken()) {
            case ttNot:
                firstOp = ExpressionOp.opNot;
                break;
            case ttMinus:
                firstOp = ExpressionOp.opMinus;
                break;
        }
        if (firstOp != ExpressionOp.opNone) {
            tkn = FTokenizer.GetNextToken();
        }

        switch (tkn.getToken()) {
            case ttMinusMinus:
                secondOp = ExpressionOp.opMinusMinus;
                break;
            case ttPlusPlus:
                secondOp = ExpressionOp.opPlusPlus;
                break;
        }
        //todo -carray: checar se o valor é um array

        /*
          0 0 = 0
          0 1 = 1
          1 0 = 0
          1 1 = 1  */
        if (secondOp != ExpressionOp.opNone) {
            tkn = FTokenizer.GetNextToken();
        }

        switch (tkn.getToken()) {
            case ttBLeft:
                if (!LeComNivels(Expr, Result, ExpressionStep.esSubExpr)) {
                    return false;
                }
                break;
            case ttName:
                String name = (String) tkn.getData();

                if (Expr.ExitsVar(name, true)) {
                    VaribleExpr varExp = new VaribleExpr(name, Expr);
                    Result.setValue(varExp);
                } /*else if (FFunctions -> Find(name, i)) {
                    tkn = FTokenizer.GetNextToken();
                    if (tkn.getToken() != Token.ttBLeft) {
                        //raise an exception
                        logError(Error.EFUNCNOELEFT);
                        return false;
                    }
                    FuncExpr fe = new FuncExpr(name);
                    Result.setValue(fe);

                    if (!_ReadFuncParams(fe)) {
                        return false;
                    }
                } */ else if (FUserFunctions.containsKey(name)) {

                    UserFuncTemp UserFuncTemp = FUserFunctions.get(name);
                    UserFuncCaller UserFuncExpr = new UserFuncCaller(Expr, UserFuncTemp);
                    Reference<EvaluableExpr> EvalExpr = new Reference<>();

                    Result.setValue(UserFuncExpr);

                    tkn = FTokenizer.GetNextToken();
                    if (tkn.getToken() != Token.ttBLeft) {
                        //raise an exception
                        logError(Error.EFUNCNOELEFT);
                        return false;
                    }

                    if (UserFuncTemp.getTotalParameters() > 0) {
                        int pCount = 0;

                        while (FLastTkn.getToken() != Token.ttBRight && FLastTkn.getToken() != Token.ttEof) {
                            //we have a value parameter
                            if (!LeComNivels(Expr, EvalExpr, ExpressionStep.esFunction)) {
                                return false;
                            }
                            UserFuncExpr.AddArg(EvalExpr.getValue());
                            pCount++;
                        }

                        if (pCount != UserFuncTemp.getTotalParameters()) {
                            logError(Error.ENMBROFPARAMS);
                            return false;
                        }

                    } else if (FTokenizer.GetNextToken().getToken() != Token.ttBRight) {
                        logError(Error.EFUNCBRIGHT);
                        return false;
                    }
                    //tkn = FTokenizer->GetNextToken();
                    //if (tkn.Token != ttSemicolon)
                    //{
                    //LogError(Error.ESEMICOLON);
                    //return false;
                    //}

                    return true;
                } else {
                    logError(Error.VARNOTFOUND);
                    return false;
                }
                break;
            case ttValue:
                ConstExpr constExp = new ConstExpr(tkn.getData());
                Result.setValue(constExp);
                break;
        }

        OldR = Result.getValue();

        switch (secondOp) {
            case opPlusPlus:
                if (!(OldR instanceof VaribleExpr)) {
                    logError(Error.EPREINC);
                    return false;
                }

                PreIncVarible piv = new PreIncVarible(OldR);
                Result.setValue(piv);
                OldR = Result.getValue();

                break;
            case opMinusMinus:
                if (!(OldR instanceof VaribleExpr)) {
                    logError(Error.EPREINC);
                    return false;
                }
                PreDecVarible pdv = new PreDecVarible(OldR);
                Result.setValue(pdv);
                OldR = Result.getValue();

                break;
            case opNone:
                //maybe we have a pos-[inc/dec]
                Bookmark Book = FTokenizer.GetBookmark();
                tkn = FTokenizer.GetNextToken();
                if (tkn.getToken() == Token.ttPlusPlus) {

                    if (!(OldR instanceof VaribleExpr)) {
                        logError(Error.EPOSINC);
                        return false;
                    }

                    PosIncVarible pic = new PosIncVarible(OldR);
                    Result.setValue(pic);
                    OldR = Result.getValue();

                } else if (tkn.getToken() == Token.ttMinusMinus) {

                    if (!(OldR instanceof VaribleExpr)) {
                        logError(Error.EPOSDEC);
                        return false;
                    }

                    PosDecVarible psdv = new PosDecVarible(OldR);
                    Result.setValue(psdv);
                    OldR = Result.getValue();

                } else /* TODO 3 -cbook: qnd da bookmark, o token antigo tem que ser desalocado
                    se o token, fosse ttName, um char * estaria alocado */ {
                    FTokenizer.GotoBookmark(Book);
                }
                break;
        }

        switch (firstOp) {
            case opNot:
                NotExpr notEx = new NotExpr(OldR);
                Result.setValue(notEx);
                OldR = Result.getValue();
                break;
            case opMinus:
                NegExpr negEx = new NegExpr(OldR);
                Result.setValue(negEx);
                OldR = Result.getValue();
                break;
        }

        return (Result.getValue() != null);
    }

    private int GetOperatorLevel(Token token) {
        switch (token) {
            case ttTimes:
            case ttDiv:
            case ttMod:
                return 1;
            case ttPlus:
            case ttMinus:
                return 2;
            case ttBigger:
            case ttBEqual:
            case ttSmall:
            case ttSEqual:
            case ttIsEqual:
            case ttNotEqual:
                return 3;
            case ttOr:
            case ttAnd:
                return 4;
            default:
                return -1;
        }
    }

    private boolean ReadIf(InstructLstExpr Expr) {
        IfExpr IfExpr;
        Reference<EvaluableExpr> Condition = new Reference<>();
        Bookmark Book;
        StreamToken tkn;

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttBLeft) {
            //if nao seguido de parentes, gerar erro
            logError(Error.EBLEFTIF);
            return false;
        }

        if (!LeComNivels(Expr, Condition, ExpressionStep.esSubExpr)) {
            //erro ao ler condicao, erro gerado
            return false;
        }

        IfExpr = new IfExpr(Expr, Condition.getValue());

        if (!ReadBlock(IfExpr, false)) {
            return false;
        }

        Book = FTokenizer.GetBookmark();
        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() == Token.ttElse) {
            if (!ReadBlock(IfExpr.getElseExpr(), false)) {
                return false;
            }
        } else /* TODO 3 -cbook: qnd da bookmark, o token antigo tem que ser desalocado
               se o token, fosse ttName, um char * estaria alocado */ {
            FTokenizer.GotoBookmark(Book);
        }

        Expr.AddItem(IfExpr);

        return true;
    }

    private boolean ReadBlock(InstructLstExpr Expr, boolean ForceBegin) {
        Bookmark Book;
        StreamToken tkn;

        Book = FTokenizer.GetBookmark();
        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() == Token.ttBegin) {
            while (true) {
                if (!ReadStmnt(Expr)) {
                    if (FLastTkn.getToken() != Token.ttEnd) {
                        return false;
                    }
                    return true;
                }
                Book = FTokenizer.GetBookmark();
                tkn = FTokenizer.GetNextToken();
                if (tkn.getToken() == Token.ttEnd) {
                    return true;
                } else /* TODO 3 -cbook: qnd da bookmark, o token antigo tem que ser desalocado
               se o token, fosse ttName, um char * estaria alocado */ {
                    FTokenizer.GotoBookmark(Book);
                }
            }
        } else {
            if (ForceBegin) {
                logError(Error.EBEGIN);
                return false;
            }
            /* TODO 3 -cbook: qnd da bookmark, o token antigo tem que ser desalocado
            se o token, fosse ttName, um char * estaria alocado */
            FTokenizer.GotoBookmark(Book);
            return ReadStmnt(Expr);
        }
    }

    private boolean ReadWhile(InstructLstExpr Expr) {
        WhileExpr WhileExpr;
        Reference<EvaluableExpr> Condition = new Reference<>();
        StreamToken tkn;

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() == Token.ttBLeft) {
            if (!LeComNivels(Expr, Condition, ExpressionStep.esSubExpr)) {
                //erro ao ler condicao, erro ja gerado
                return false;
            }

            WhileExpr = new WhileExpr(Expr, Condition.getValue());

            if (!ReadBlock(WhileExpr, false)) {
                return false;
            }

            Expr.AddItem(WhileExpr);
            return true;
        } else {
            //while nao seguido de parentes, gerar erro
            logError(Error.EBLEFT);
            return false;
        }
    }

    private boolean ReadDoWhile(InstructLstExpr Expr) {
        DoWhileExpr DoWhileExpr;
        Reference<EvaluableExpr> Condition = new Reference<>();
        StreamToken tkn;

        DoWhileExpr = new DoWhileExpr(Expr, null);

        if (!ReadBlock(DoWhileExpr, false)) {
            return false;
        }

        if ((tkn = FTokenizer.GetNextToken()).getToken() != Token.ttWhile) {
            /* gerar erro: while esperado mas nao foi encontrado */
            logError(Error.EWHILE);
            return false;
        }

        if ((tkn = FTokenizer.GetNextToken()).getToken() != Token.ttBLeft) {
            //"(" esperado mas nao encontrado
            logError(Error.EBLEFT);
            return false;
        }

        if (!LeComNivels(Expr, Condition, ExpressionStep.esSubExpr)) {
            //erro ao ler condicao, erro ja gerado
            return false;
        }

        DoWhileExpr.setCondition(Condition.getValue());

        if ((tkn = FTokenizer.GetNextToken()).getToken() != Token.ttSemicolon) {
            //";" esperado mas nao encontrado
            logError(Error.ESEMICOLON);
            return false;
        }
        Expr.AddItem(DoWhileExpr);
        return true;
    }

    private boolean ReadFor(InstructLstExpr Expr) {
        StreamToken tkn;
        ForExpr ForExpr;
        Reference<EvaluableExpr> Condition = new Reference<>();
        Reference<Expr> InitExpr = new Reference<>();
        Reference<Expr> IncExpr = new Reference<>();

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttBLeft) {
            //for tem que ser seguido de "("
            logError(Error.EBLEFTFOR);
            return false;
        }

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttSemicolon) {
            if (tkn.getToken() != Token.ttName) {
                logError(Error.EVARNAMENTFND);
                return false;
            }
            if (!ReadExpr(Expr, tkn, InitExpr, ExpressionStep.esNone)) {
                return false;
            }
        }

        if (!LeComNivels(Expr, Condition, ExpressionStep.esNone)) {
            //erro ao ler expressao de condicao, erro gerado
            return false;
        }
        /*TODO: aki esta deixando a condicao do "for" obrigatoria, melhorar isso
        else if (Condition->GetCount() == 0)
        {
           delete Condition;
           Condition = NULL;
        } */

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttBRight) {
            if (tkn.getToken() != Token.ttName) {
                logError(Error.EVARNAMENTFND);
                return false;
            }
            if (!ReadExpr(Expr, tkn, IncExpr, ExpressionStep.esSubExpr)) {
                return false;
            }
        }

        ForExpr = new ForExpr(Expr, InitExpr.getValue(), Condition.getValue(), IncExpr.getValue());

        if (ReadBlock(ForExpr, false)) {
            Expr.AddItem(ForExpr);
            return true;
        }

        return false;
    }

    private boolean ReadFunction(InstructLstExpr Expr) {
        StreamToken tkn;
        String funcName;
        UserFuncTemp UserFunc;

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttName) {
            logError(Error.EFUNCNAME);
            return false;
        }

        funcName = (String) tkn.getData();

        // TODO check if it is a system function
        if (FUserFunctions.containsKey(funcName)) {
            logError(Error.EFUNCEXISTS);
            return false;
        }

        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttBLeft) {
            logError(Error.EFUNCBLEFT);
            return false;
        }

        UserFunc = new UserFuncTemp(Expr);
        tkn = FTokenizer.GetNextToken();
        if (tkn.getToken() != Token.ttBRight) {
            while (true) {
                if (tkn.getToken() != Token.ttName) {
                    logError(Error.EVARNAMENTFND);
                    return false;
                }
                UserFunc.addParameter((String) tkn.getData());
                tkn = FTokenizer.GetNextToken();
                if (tkn.getToken() == Token.ttBRight) {
                    break;
                } else if (tkn.getToken() != Token.ttComma) {
                    logError(Error.ECOMMA);
                    return false;
                }
                tkn = FTokenizer.GetNextToken();
            }
        }
        FCurUserFunc = UserFunc;
        if (!ReadBlock(UserFunc.getBody(), true)) {
            return false;
        }
        FUserFunctions.put(funcName, UserFunc);
        return true;
    }

    private boolean ReadReturn(InstructLstExpr Expr) {
        Reference<EvaluableExpr> returnExpr = new Reference<>();
        StreamToken tkn;
        Bookmark book;

        if (!FReadingFunction) {
            logError(Error.ERETURNFUNC);
            return false;
        }

        book = FTokenizer.GetBookmark();
        tkn = FTokenizer.GetNextToken();

        if (tkn.getToken() != Token.ttSemicolon) {
            FTokenizer.GotoBookmark(book);
            if (!LeComNivels(Expr, returnExpr, ExpressionStep.esNone)) {
                return false;
            }
        }

        ReturnExpr ReturnExpr = new ReturnExpr(Expr, returnExpr.getValue());
        Expr.AddItem(ReturnExpr);
        return true;
    }
}
