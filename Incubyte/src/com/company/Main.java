package com.company;
import java.util.*;

class Calculator{
    String s;

    public Calculator(String input){
        s=input;
    }

    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        if (l1 != l2) {
            return l1 - l2;
        }

        else {
            return 0;
        }
    }

    public String convertcharacterstonumbers(String delimiter,int start){
        String parameter="";
        int l=start;
        for(int i=start;i<s.length();++i){


            if(i<s.length()-delimiter.length()+1 && stringCompare(delimiter,s.substring(i,i+delimiter.length()))==0){
                parameter=parameter.concat(s.substring(l,i));
                parameter=parameter.concat(",");
                i+=delimiter.length()-1;
                l=i+1;
                continue;
            }

            if(s.charAt(i)>='a' && s.charAt(i)<='z'){

                parameter=parameter.concat(s.substring(l,i));
                int character = s.charAt(i);

                character -=96;

                String temp=Integer.toString(character);

                parameter=parameter.concat(temp);
                l=i+1;
            }
        }

        if(l!=s.length()){
            parameter=parameter.concat(s.substring(l,s.length()));
        }

        return parameter;
    }

    public void validate(String delimiter,int start){
        s=convertcharacterstonumbers(delimiter,start);
    }

    public int add(){
        try {
            int ans = 0;

            int l = 0;

            StringBuilder negative = new StringBuilder();

            String delimiter = "\n";

            int start = 0;

            int i = 0;
            if (s.length() > 1 && stringCompare(s.substring(0, 2), "//") == 0) {
                for (i = 2; i < s.length() - 1; ++i) {
                    if (stringCompare("\n", s.substring(i, i + 1)) == 0) {
                        delimiter = s.substring(2, i);
                        start = i + 1;
                        break;
                    }
                }
            } else {
                start = i;
            }

            validate(delimiter, start);
            for (i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == ',') {
                    String number = s.substring(l, i);
                    int y = Integer.parseInt(number);
                    if (y < 0) {
                        if (negative.length() == 0) {
                            negative = new StringBuilder(number);
                        } else {
                            negative.append(",").append(number);
                        }
                    } else if (y > 1000) {

                    } else {
                        ans += y;
                    }
                    l = i + 1;
                }
            }

            if (l != s.length()) {
                String number = s.substring(l, s.length());
                int y = Integer.parseInt(number);
                if (y < 0) {
                    if (negative.length() == 0) {
                        negative = new StringBuilder(number);
                    } else {
                        negative.append(",").append(number);
                    }
                } else {
                    ans += y;
                }
            }

            if (negative.length() > 0) {
                throw new IllegalArgumentException("Negatives not allowed: " + negative);
            }
            return ans;
        }
        catch(Exception e) {
            return 0;
        }
    }
}

class ValidatorTests {
    private static final String TEST_ONE = "1,2";
    private static final String TEST_TWO = "1,2,3,4,5,6";
    private static final String TEST_THREE = "a,b,c";
    private static final String TEST_FOUR = "1,3000,5,10";
    private static final String TEST_FIVE = "1\n2\n3";
    private static final String TEST_SIX = "//;\n1;2;3;4";
    private static final String TEST_SEVEN = "1\n-2\n3";
    private static final String TEST_EIGHT = "//;\n-1;2;-3;4";

    private static final String SUCCESSFUL = " was successful";
    private static final String FAILED = " has failed";

    public void test(){
        testValidNames();
    }
    public static void main(String[] args) {

    }

    /**
     * Success only if true (valid) is returned
     */

    private static void testValidNames() {
        Calculator Validator=new Calculator(TEST_ONE);
        if(Validator.add()==3) testLogger("TEST_ONE", SUCCESSFUL);
        else testLogger("TEST_ONE", FAILED);

        Validator=new Calculator(TEST_TWO);
        if(Validator.add()==21) testLogger("TEST_TWO", SUCCESSFUL);
        else testLogger("TEST_TWO", FAILED);

        Validator=new Calculator(TEST_THREE);
        if(Validator.add()==6) testLogger("TEST_THREE", SUCCESSFUL);
        else testLogger("TEST_THREE", FAILED);

        Validator=new Calculator(TEST_FOUR);
        if(Validator.add()==16) testLogger("TEST_FOUR", SUCCESSFUL);
        else testLogger("TEST_FOUR", FAILED);

        Validator=new Calculator(TEST_FIVE);
        if(Validator.add()==6) testLogger("TEST_FIVE", SUCCESSFUL);
        else testLogger("TEST_FIVE", FAILED);

        Validator=new Calculator(TEST_SIX);
        if(Validator.add()==10) testLogger("TEST_SIX", SUCCESSFUL);
        else testLogger("TEST_SIX", FAILED);

        Validator=new Calculator(TEST_SEVEN);
        if(Validator.add()==0) testLogger("TEST_SEVEN", SUCCESSFUL);
        else testLogger("TEST_SEVEN", FAILED);

        Validator=new Calculator(TEST_EIGHT);
        if(Validator.add()==0) testLogger("TEST_EIGHT", SUCCESSFUL);
        else testLogger("TEST_EIGHT", FAILED);
    }

    /**
     * Success only if false (invalid) is returned
     */


    private static void testLogger(String testName, String result) {
        System.out.println("Test " + testName + result);
    }
}

public class Main {

    public static void main(String[] args) {
            ValidatorTests v = new ValidatorTests();

            v.test();
    }
}
