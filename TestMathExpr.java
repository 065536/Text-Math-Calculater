import java.util.*;
public class TestMathExpr{
    public static Double parse(String str){
        try{
            StringBuilder Str = new StringBuilder(str);
            if(str.charAt(0) == '-'){
                Str.insert(0, '0');
                str = Str.toString();
            }

            int length = str.length();
            for (int i = 0; i< length-1; i++){
                if(
                    str.charAt(i) == '(' & str.charAt(i+1)== '-' ){
                    Str.insert(i+1, '0');
                }
            }

            str = Str.toString();
            List<String> list = new ArrayList<>();
            char[] arr = str.toCharArray();
            StringBuffer tmpStr = new StringBuffer();
            for(char c : arr){
                if (c >= '0' && c <= '9'){
                    tmpStr.append(c);
                }
                else if(c == '.'){
                    tmpStr.append(c);
                }
                else if(Character.isAlphabetic(c)){
                    tmpStr.append(c);
                }
                else if(c =='+'||c=='-'||c=='*'||c=='/'||c=='('||c==')'){
                    if(tmpStr.length()>0){
                        list.add(tmpStr.toString());
                        tmpStr.setLength(0);
                    }
                    list.add(c+"");
                }
            }
            if(tmpStr.length()>0){
                list.add(tmpStr.toString());
            }   
            
            Stack<String> operator = new Stack<String>();
            List<String> expression = new ArrayList<>();
            List<String> list_operator = Arrays.asList("+", "-", "*", "/");
            List<String> list_special = Arrays.asList("sin", "cos", "tan", "sqrt");
            String op_out = "";
            for(String ch: list){
                if(ch.equals("(")){
                    operator.push(ch);
                }

                else if(ch.equals(")")){
                    while(!operator.isEmpty()){
                        op_out = operator.peek();
                        if(op_out.equals("(")){
                            operator.pop();
                            break;
                            }
                        else{
                            expression.add(op_out);
                            operator.pop();
                            }
                        }
                    }

                else if (list_special.contains(ch)){
                    while(!operator.isEmpty()){
                        op_out = operator.peek();
                        if(list_operator.contains(op_out)| op_out.equals("(")){
                            break;
                        }
                        else{
                            operator.pop();
                            expression.add(op_out);
                        }
                    }
                    operator.push(ch);
                }

                else if(ch.equals("*")|ch.equals("/")){
                    while(!operator.isEmpty()){
                        op_out = operator.peek();
                        if(op_out.equals("+") | op_out.equals("-") | op_out.equals("(")){
                            break;
                        }
                        else{
                            operator.pop();
                            expression.add(op_out);
                        }
                    }
                    operator.push(ch);
                }

                else if(ch.equals("+")|ch.equals("-")){
                    while(!operator.isEmpty()){
                        op_out = operator.peek();
                        if(op_out.equals("(")){
                            break;
                        }
                        else{
                            operator.pop();
                            expression.add(op_out);
                        }
                    }
                    operator.push(ch);
                }

                else{
                    expression.add(ch);
                }
            }

            while(!operator.isEmpty()){
                expression.add(operator.pop());
            }
            Stack<Double> result = new Stack<>(); 
            for(String top: expression){
                Double result_temp = 0.0;
                if(list_operator.contains(top)|list_special.contains(top)){
                    result_temp = calculation(top, result);
                    result.push(result_temp);
                }
                else{
                    result.push(Double.parseDouble(top));
                }
            }
            return result.pop();
        }
        
        catch(Exception e){
            return Double.NaN;
        }
    }

    public static Double calculation(String str, Stack<Double> st){
        Double store_float = st.peek();
        Double outcome = 0.0;
        switch(str){
            case "+":
            outcome = st.pop() + st.pop();
            break;

            case "-":
            st.pop();
            outcome = st.pop() - store_float;
            break;

            case "*":
            outcome = st.pop() * st.pop();
            break;

            case "/":
            st.pop();
            outcome = st.pop() / store_float;
            break;

            case "sin":
            outcome = Math.sin(st.pop());
            break;

            case "cos":
            outcome = Math.cos(st.pop());
            break;

            case "tan":
            outcome = Math.tan(st.pop());
            break;

            case "sqrt":
            outcome = Math.sqrt(st.pop());
            break;
        }
        return outcome;

    }

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()){
            double result = parse(input.nextLine());
            if(Double.isNaN(result)){
                System.out.println("invalid");
            }
            else{
                System.out.println(String.valueOf(Math.round(result)));
            }

        }
    }
}