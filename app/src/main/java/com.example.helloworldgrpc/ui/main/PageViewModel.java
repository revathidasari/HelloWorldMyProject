package com.example.helloworldgrpc.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            switch (input) {
                case 1 : return "#include <stdio.h>\n" +
                        "int main() {\n" +
                        "  int t1 = 0, t2 = 1, nextTerm = 0, n;\n" +
                        "  printf(\"Enter a positive number: \");\n" +
                        "  scanf(\"%d\", &n);\n" +
                        "\n" +
                        "  // displays the first two terms which is always 0 and 1\n" +
                        "  printf(\"Fibonacci Series: %d, %d, \", t1, t2);\n" +
                        "  nextTerm = t1 + t2;\n" +
                        "\n" +
                        "  while (nextTerm <= n) {\n" +
                        "    printf(\"%d, \", nextTerm);\n" +
                        "    t1 = t2;\n" +
                        "    t2 = nextTerm;\n" +
                        "    nextTerm = t1 + t2;\n" +
                        "  }\n" +
                        "\n" +
                        "    char c;\n" +
                        "    printf(\"Enter a character: \");\n" +
                        "    scanf(\"%c\", &c);  \n" +
                        "    \n" +
                        "    // %d displays the integer value of a character\n" +
                        "    // %c displays the actual character\n" +
                        "    printf(\"ASCII value of %c = %d\", c, c);"+
                        "  return 0;\n" +
                        "}\n\n" +
                        "OUTPUT : Enter a positive integer: 100\n" +
                        "Fibonacci Series: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ;\n"+
                        "Enter a character: G\n" +
                        "ASCII value of G = 71"
                        +"\n                   FIBONICCI SERIES UNTIL THE NUMBER & CHARACTER'S ASCII VALUE";
                case 2 : return "#include <iostream>\n" +
                        "using namespace std;\n" +
                        "\n" +
                        "int main()\n" +
                        "{\n" +
                        "     int n, num, digit, rev = 0;\n" +
                        "\n" +
                        "     cout << \"Enter a positive number: \";\n" +
                        "     cin >> num;\n" +
                        "     n = num;\n" +
                        "\n" +
                        "     do\n" +
                        "     {\n" +
                        "         digit = num % 10;\n" +
                        "         rev = (rev * 10) + digit;\n" +
                        "         num = num / 10;\n" +
                        "     } while (num != 0);\n" +
                        "\n" +
                        "     cout << \" The reverse of the number is: \" << rev << endl;\n" +
                        "\n" +
                        "     if (n == rev)\n" +
                        "         cout << \" The number is a palindrome.\";\n" +
                        "     else\n" +
                        "         cout << \" The number is not a palindrome.\";\n" +
                        "\n" +
                        "    return 0;\n" +
                        "}\n" +
                        "\n OUTPUT : Enter a positive number: 12321\n" +
                        "The reverse of the number is: 12321\n" +
                        "The number is a palindrome.\n"
                        +"                    PALINDROME NUMBER CHECK";
                case 3 : return "class FactorsOfANumber {\n" +
                        "\n" +
                        "  public static void main(String[] args) {\n" +
                        "\n" +
                        "    // negative number\n" +
                        "    int number = -60;\n" +
                        "    System.out.print(\"Factors of \" + number + \" are: \");\n" +
                        "\n" +
                        "    // run loop from -60 to 60\n" +
                        "    for(int i = number; i <= Math.abs(number); ++i) {\n" +
                        "// for positive replace with this line \t    for (int i = 1; i <= number; ++i) {\n" +
                        "\n" +
                        "      // skips the iteration for i = 0\n" +
                        "      if(i == 0) {\n" +
                        "        continue;\n" +
                        "      }\n" +
                        "      else {\n" +
                        "        if (number % i == 0) {\n" +
                        "          System.out.print(i + \" \");\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n\n" +
                        "OUTPUT : Factors of -60 are: -60 -30 -20 -15 -12 -10 -6 -5 -4 -3 -2 -1 1 2 3 4 5 6 10 12 15 20 30 60"
                        +"\n\n                    FACTORS OF A NUMBER";
                case 4 : return "1)\nnum = 1234\n" +
                        "reversed_num = 0\n" +
                        "\n" +
                        "while num != 0:\n" +
                        "    digit = num % 10\n" +
                        "    reversed_num = reversed_num * 10 + digit\n" +
                        "    num //= 10\n" +
                        "\n" +
                        "print(\"Reversed Number: \" + str(reversed_num))\n" +
                        "\n" +
                        "OUTPUT : 4321"+
                        "\n\n2)\nAnother way Using String slicing\n" +
                        "\n" +
                        "num = 123456\n" +
                        "print(str(num)[::-1])\n" +
                        "\n" +
                        "OUTPUT : \n" +
                        "\n" +
                        "654321\n\n"
                        +"                         NUMBER REVERSING PROGRAM";
                default: break;
            }

            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}