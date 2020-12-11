import java.util.ArrayList;
import java.util.List;

public class OperatorTree {
    public static List<TokenType> types=new ArrayList<>();
    public static List<Integer> stack=new ArrayList<>();
    //+ - * / ( ) < > <= >= == != -
    static int priority[][]={
            {1,1,-1,-1,-1,1,   1,1,1,1,1,1,  -1},
            {1,1,-1,-1,-1,1,  1,1,1,1,1,1,   -1},
            {1,1,1,1,-1,1,   1,1,1,1,1,1,    -1},
            {1,1,1,1,-1,1,   1,1,1,1,1,1,   -1},

            {-1,-1,-1,-1,-1,100,   -1,-1,-1,-1,-1,-1,   -1},
            {-1,-1,-1,-1,0,0   ,    -1,-1,-1,-1,-1,-1   ,-1},

            {-1,-1,-1,-1,-1,1,  1,1,1,1,1,1,      -1},
            {-1,-1,-1,-1,-1,1,  1,1,1,1,1,1,      -1},
            {-1,-1,-1,-1,-1,1,  1,1,1,1,1,1,      -1},
            {-1,-1,-1,-1,-1,1,  1,1,1,1,1,1,      -1},
            {-1,-1,-1,-1,-1,1,  1,1,1,1,1,1,      -1},
            {-1,-1,-1,-1,-1,1,  1,1,1,1,1,1,      -1},

            {1,1,1,1,-1,1,     1,1,1,1,1,1    ,-1}

    };

    static int getInt(TokenType tokenType){
        if(tokenType== TokenType.PLUS){
            return 0;
        }else if(tokenType== TokenType.MINUS){
            return 1;
        }else if(tokenType== TokenType.MUL){
            return 2;
        }else if(tokenType== TokenType.DIV){
            return 3;
        }else if(tokenType== TokenType.L_PAREN){
            return 4;
        }else if(tokenType== TokenType.R_PAREN){
            return 5;
        }
        else if(tokenType== TokenType.LT){
            return 6;
        }
        else if(tokenType== TokenType.GT){
            return 7;
        }
        else if(tokenType== TokenType.LE){
            return 8;
        }
        else if(tokenType== TokenType.GE){
            return 9;
        }
        else if(tokenType== TokenType.EQ){
            return 10;
        }
        else if(tokenType== TokenType.NEQ){
            return 11;
        }else if(tokenType== TokenType.NEG){
            return 12;
        }
        return -1;
    }

    private static List<Instruction> addInstruction(int top) throws AnalyzeError {
        List<Instruction> instructions=new ArrayList<>();
        int toptype=types.size()-1;
        if(top==0){
            if(types.get(toptype)==types.get(toptype-1)){
                if (types.get(toptype)==TokenType.INT_KW)
                    instructions.add(new Instruction(Operation.add_i));
                else if(types.get(toptype)==TokenType.DOUBLE_KW)
                    instructions.add(new Instruction(Operation.add_f));
                else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
                types.remove(toptype);
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
        }else if(top==1){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype)==TokenType.INT_KW)
                    instructions.add(new Instruction(Operation.sub_i));
                else if(types.get(toptype)==TokenType.DOUBLE_KW)
                    instructions.add(new Instruction(Operation.sub_f));
                else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
                types.remove(toptype);
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
        }else if(top==2){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW)
                    instructions.add(new Instruction(Operation.mul_i));
                else if(types.get(toptype)==TokenType.DOUBLE_KW)
                    instructions.add(new Instruction(Operation.mul_f));
                else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
                types.remove(toptype);
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
        }else if (top==3){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW)
                    instructions.add(new Instruction(Operation.div_i)) ;
                else if(types.get(toptype)==TokenType.DOUBLE_KW)
                    instructions.add(new Instruction(Operation.div_f));
                else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
                types.remove(toptype);
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
        }
        else if (top==6){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW){
                    //true -1 false 0 1
                    instructions.add(new Instruction(Operation.cmp_i));
                    //true 1 false 0
                    instructions.add(new Instruction(Operation.set_lt));
                }else if(types.get(toptype)==TokenType.DOUBLE_KW){
                    //true -1 false 0 1
                    instructions.add(new Instruction(Operation.cmp_f));
                    //true 1 false 0
                    instructions.add(new Instruction(Operation.set_lt));
                }else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
                types.remove(toptype);
                types.remove(toptype-1);
                types.add(TokenType.BOOLEAN_KW);

        }else if (top==7){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW) {
                    //true 1 false 0 -1
                    instructions.add(new Instruction(Operation.cmp_i));
                    //true 1 false 0
                    instructions.add(new Instruction(Operation.set_gt));
                }
                else if(types.get(toptype)==TokenType.DOUBLE_KW){
                    //true 1 false 0 -1
                    instructions.add(new Instruction(Operation.cmp_f));
                    //true 1 false 0
                    instructions.add(new Instruction(Operation.set_gt));
                }else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
            types.remove(toptype);
            types.remove(toptype-1);
            types.add(TokenType.BOOLEAN_KW);
        }else if (top==8){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW) {
                    //true -1 0 false 1
                    instructions.add(new Instruction(Operation.cmp_i));

                    //true 0 false 1
                    instructions.add(new Instruction(Operation.set_gt));

                    //true 1 false 0
                    instructions.add(new Instruction(Operation.not));
                }else if (types.get(toptype) == TokenType.DOUBLE_KW) {
                    //true -1 0 false 1
                    instructions.add(new Instruction(Operation.cmp_f));

                    //true 0 false 1
                    instructions.add(new Instruction(Operation.set_gt));

                    //true 1 false 0
                    instructions.add(new Instruction(Operation.not));
                }else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
            types.remove(toptype);
            types.remove(toptype-1);
            types.add(TokenType.BOOLEAN_KW);
        }else if (top==9){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW) {
                    //true 1 0 false -1
                    instructions.add(new Instruction(Operation.cmp_i));
                    //true 0 false 1
                    instructions.add(new Instruction(Operation.set_lt));

                    //true 1 false 0
                    instructions.add(new Instruction(Operation.not));
                }else if (types.get(toptype) == TokenType.DOUBLE_KW) {
                    //true 1 0 false -1
                    instructions.add(new Instruction(Operation.cmp_f));
                    //true 0 false 1
                    instructions.add(new Instruction(Operation.set_lt));

                    //true 1 false 0
                    instructions.add(new Instruction(Operation.not));
                }else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
            types.remove(toptype);
            types.remove(toptype-1);
            types.add(TokenType.BOOLEAN_KW);
        }else if (top==10){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW) {
                    //true 0 false 1 -1
                    instructions.add(new Instruction(Operation.cmp_i));

                    //true 1 false 0
                    instructions.add(new Instruction(Operation.not));
                }else if (types.get(toptype) == TokenType.DOUBLE_KW) {
                    //true 0 false 1 -1
                    instructions.add(new Instruction(Operation.cmp_f));

                    //true 1 false 0
                    instructions.add(new Instruction(Operation.not));
                }else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));

            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
            types.remove(toptype);
            types.remove(toptype-1);
            types.add(TokenType.BOOLEAN_KW);
        }else if (top==11){
            if(types.get(toptype)==types.get(toptype-1)) {
                if (types.get(toptype) == TokenType.INT_KW) {
                    //true 1 -1 false 0
                    instructions.add(new Instruction(Operation.cmp_i));
                }else if (types.get(toptype) == TokenType.DOUBLE_KW) {
                    //true 1 -1 false 0
                    instructions.add(new Instruction(Operation.cmp_f));
                }else
                    throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
            }
            else
                throw new AnalyzeError(ErrorCode.WrongOparatorType, new Pos(-1,-1));
            types.remove(toptype);
            types.remove(toptype-1);
            types.add(TokenType.BOOLEAN_KW);
        }else if (top==12){
            if (types.get(toptype) == TokenType.INT_KW)
                instructions.add(new Instruction(Operation.neg_i));
            else if (types.get(toptype) == TokenType.DOUBLE_KW)
                instructions.add(new Instruction(Operation.neg_f));
            else
                throw new AnalyzeError(ErrorCode.OpetateToBoolean, new Pos(-1,-1));
        }
        return instructions;
    }

    public static List<Instruction> addAllReset() throws AnalyzeError {
        List<Instruction> instructions=new ArrayList<>();
        for(int i=stack.size()-1;i>=0;i--){
            instructions.addAll(addInstruction(stack.get(i)));
            stack.remove(i);
        }
        if(types.size()==2&&types.get(0)!=types.get(1))
            throw new AnalyzeError(ErrorCode.AssignToWrongType, new Pos(-1,-1));
        else {
            for(int i=0;i<types.size();i++)
                types.remove(i);
        }
        return instructions;
    }

    public static List<Integer> getStack() {
        return stack;
    }

    public static List<Instruction> getNewOperator(TokenType tokenType) throws AnalyzeError {
        List<Instruction> instructions=new ArrayList<>();
        int next=getInt(tokenType);
        if (stack.size()<1){
            stack.add(next);
            return instructions;
        }
        int top=stack.get(stack.size()-1);

        while (priority[top][next]>0){
            stack.remove(stack.size()-1);
            instructions.addAll(addInstruction(top));
            if(top==4)
                return instructions;
            else if (stack.size()==0)
                break;
            top=stack.get(stack.size()-1);
        }
        stack.add(next);
        return instructions;
    }


}
