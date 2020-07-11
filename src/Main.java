import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String a="(";

        System.out.println(isOpenBrace(a));
        String[] myString={"1","+","(","3","-","5","*","4",")","+","2"};
//        String testString="5-2*3*4-5/9999";
//        System.out.println(isNumber(String.valueOf(testString.charAt(1))));
//        System.out.println(convertStringToList("52-2434*3*4-5/99999"));
////        System.out.println(calculate(2,3,"/"));
////        System.out.println(isGreaterOperation("+","-"));
//        System.out.println(toPostFix(myString));
//        //expect 12-34*-5+;
//
////        System.out.println(postFixToResult(toPostFix(myString)));

        System.out.println(toPostFixWithBrace(myString));

//        float result=  postFixToResult(toPostFix(myString));
//        String myValue=String.valueOf(result);
//        System.out.println() ;
//        if (myValue.charAt(myValue.indexOf(".")+1)=='0'){
//            System.out.println("Result is: "+(int)result);
//        }else{
//            System.out.println("Result is: "+result);
//        }
//        if ()
    }

    public static List<String> toPostFix(String[] input){
        ArrayList<String> postFix=new ArrayList<>();
        Stack<String> operationStack=new Stack<>();

        for (int i=0;i<input.length;i++){
            //neu vi tri dang duyet la so thi dua so vao stacknumber
            if (isNumber(input[i])){
                postFix.add(input[i]);
            }

            if (isOperation(input[i])){
                if (operationStack.isEmpty()){
                    operationStack.push(input[i]);
                }else{
                    String str1=operationStack.peek();
                    while(isGreaterOperation(str1,input[i]) ){
                        postFix.add(str1);
                        operationStack.pop();
                        if (operationStack.isEmpty()){
                            break;
                        }
                        str1=operationStack.peek();
                    }
                    operationStack.push(input[i]);
                }
            }
        }
        while(!operationStack.isEmpty()){
            postFix.add(operationStack.pop());
        }
        return postFix;
    }

    public static boolean isNumber(String str){
        String patternStr="^[\\d]+$";
        Pattern pattern=Pattern.compile(patternStr);
        Matcher matcher=pattern.matcher(str);
        return matcher.matches();
    }

    public  static boolean isGreaterOperation(String str1,String str2){
        String patternGreaterStr="^(\\*)|(/)+$";
        String patternLowerStr="^(\\+)|(-)+$";
        Pattern pattern=Pattern.compile(patternGreaterStr);

        Matcher matcher= pattern.matcher(str1);

        if (isCloseBrace(str2)){
            return true;
        }
        if (matcher.matches())
            return true;
        else{
            Pattern pattern2=Pattern.compile(patternLowerStr);
            Matcher matcher2= pattern2.matcher(str2);
            if (matcher2.matches()){
                return true;
            }else{
                return false;
            }
        }

//        return false;
    }

    public  static boolean isOperation(String str){
        String patternStr="^(\\*)|(\\+)|(-)|(/)$";
        Pattern pattern=Pattern.compile(patternStr);
        Matcher matcher=pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isOpenBrace(String str){
        String patternStr="^[(]$";
        Pattern pattern=Pattern.compile(patternStr);
        Matcher matcher=pattern.matcher(str);
        return matcher.matches();
    }


    public static boolean isCloseBrace(String str){
        String patternStr="^[)]$";
        Pattern pattern=Pattern.compile(patternStr);
        Matcher matcher=pattern.matcher(str);
        return matcher.matches();
    }

    public static float calculate(float num1,float num2,String operation){
        float total=0;
        switch(operation){
            case "-":
                total=num2-num1;
                break;
            case "+":
                total=num1+num2;
                break;
            case "*":
                total=num1*num2;
                break;
            case "/":
                total=num2/num1;
                break;
        }
        return total;
    }

    public static float postFixToResult(List<String> list){

        Stack<Float> tempStack=new Stack<>();

        for (int i=0;i<list.size();i++){
            if (isNumber(list.get(i))){
                tempStack.push(Float.parseFloat(list.get(i)));
            }
            if (isOperation(list.get(i))){
                tempStack.push(calculate(tempStack.pop(),tempStack.pop(),list.get(i)));
            }
        }

        return tempStack.pop();
    }

    public static List<String> convertStringToList(String input){
        List<String> list= new ArrayList<>();

        int count=0;
        int countOperation=0;
        int lastIndex=-1;
        for (int i=0;i<input.length();i++){

            if (isNumber(String.valueOf(input.charAt(i)))){
                count++;
            }

            if (isOperation(String.valueOf(input.charAt(i)))){
                countOperation++;
                String tempString="";
                tempString=input.substring(i-count,i);
                list.add( tempString);
                count=0;
                list.add(String.valueOf(input.charAt(i)));
                if (countOperation==countOperationInput(input))
                    lastIndex=i;
            }

        }
        list.add(input.substring(lastIndex+1));
        return list;
    }

    public static int countOperationInput(String input){
        int count=0;
        for (int i=0;i<input.length();i++){
            if (isOperation( String.valueOf(input.charAt(i)))){
                count++;
            }

        }
        return count;
    }


    public static List<String> toPostFixWithBrace(String[] input){
        ArrayList<String> postFix=new ArrayList<>();
        Stack<String> operationStack=new Stack<>();

        for (int i=0;i<input.length;i++){
            //neu vi tri dang duyet la so thi dua so vao stacknumber
            if (isNumber(input[i])){
                postFix.add(input[i]);
                continue;
            }

            if (isOpenBrace(input[i])){
                operationStack.push(input[i]);
                continue;
            }

            if (isOperation(input[i]) || isCloseBrace(input[i])){
                if (operationStack.isEmpty()){
                    operationStack.push(input[i]);
                }else{
                    String str1=operationStack.peek();
                    while(isGreaterOperation(str1,input[i]) ){
                        if (!isOpenBrace(str1) ){
                            postFix.add(str1);
                            operationStack.pop();
                        }

                        if (operationStack.isEmpty()){
                            break;
                        } else if (isOpenBrace(operationStack.peek())){
                            if (isCloseBrace(input[i])) {
                                operationStack.pop();
                            }
                            break;
                        }
                        str1=operationStack.peek();
                    }
                    if (!isCloseBrace(input[i])) operationStack.push(input[i]);
                }
            }
        }
        while(!operationStack.isEmpty()){
            postFix.add(operationStack.pop());
        }
        return postFix;
    }

}
