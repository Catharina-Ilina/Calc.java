package com.calculator.main;

import com.calculator.model.Operation;
import com.calculator.model.operations.Add;
import com.calculator.model.operations.Divide;
import com.calculator.model.operations.Multiply;
import com.calculator.model.operations.Subtract;
import com.calculator.utils.Converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {

    // Паттерны для валидации входных данных
    private static final Pattern PATTERN_ARABIC = Pattern.compile("^([1-9]|10)[*|+\\-/]([1-9]|10)$");
    private static final Pattern PATTERN_ROMAN = Pattern.compile("^([IVX]+)[*|+\\-/]([IVX]+)$");

    public static void main(String[] args) throws Exception {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("!Для выхода из программы введите символ перевода строки (Enter)!\n");
            System.out.print("Input: ");

            String inputString;
            int number1 = 0;
            int number2 = 0;
            Operation operation = null;
            int arabicResult = 0;
            String romanResult = null;
            boolean isRoman;

            while (true) {

                inputString = reader.readLine();

                if (inputString.equals("")
                        || inputString.equals(" ")
                        || inputString.equals("\n"))
                    break;

                inputString = inputString.replaceAll("\\s", "").trim();

                Matcher arabicMatcher = PATTERN_ARABIC.matcher(inputString);
                Matcher romanMatcher = PATTERN_ROMAN.matcher(inputString);

                // Если входная строка не проходит по одному из паттернов,
                // то возвращаем ошибку и завершаем работу программы
                if (!arabicMatcher.matches() && !romanMatcher.matches()) {
                    throw new Exception("Некорректный ввод!");
                }

                isRoman = romanMatcher.matches();

                // Получаем тип операции и значения переменных
                if (inputString.contains("+")) {
                    operation = Operation.PLUS;
                    if (isRoman) {
                        number1 = Converter.romanToArabic(inputString.substring(0, inputString.indexOf("+")));
                        number2 = Converter.romanToArabic(inputString.substring(inputString.indexOf("+") + 1));
                    } else {
                        number1 = Integer.parseInt(inputString.substring(0, inputString.indexOf("+")));
                        number2 = Integer.parseInt(inputString.substring(inputString.indexOf("+") + 1));
                    }

                } else if (inputString.contains("-")) {
                    operation = Operation.MINUS;
                    if (isRoman) {
                        number1 = Converter.romanToArabic(inputString.substring(0, inputString.indexOf("-")));
                        number2 = Converter.romanToArabic(inputString.substring(inputString.indexOf("-") + 1));
                    } else {
                        number1 = Integer.parseInt(inputString.substring(0, inputString.indexOf("-")));
                        number2 = Integer.parseInt(inputString.substring(inputString.indexOf("-") + 1));
                    }
                } else if (inputString.contains("*")) {
                    operation = Operation.MULTIPLY;
                    if (isRoman) {
                        number1 = Converter.romanToArabic(inputString.substring(0, inputString.indexOf("*")));
                        number2 = Converter.romanToArabic(inputString.substring(inputString.indexOf("*") + 1));
                    } else {
                        number1 = Integer.parseInt(inputString.substring(0, inputString.indexOf("*")));
                        number2 = Integer.parseInt(inputString.substring(inputString.indexOf("*") + 1));
                    }
                } else if (inputString.contains("/")) {
                    operation = Operation.DIVIDE;
                    if (isRoman) {
                        number1 = Converter.romanToArabic(inputString.substring(0, inputString.indexOf("/")));
                        number2 = Converter.romanToArabic(inputString.substring(inputString.indexOf("/") + 1));
                    } else {
                        number1 = Integer.parseInt(inputString.substring(0, inputString.indexOf("/")));
                        number2 = Integer.parseInt(inputString.substring(inputString.indexOf("/") + 1));
                    }
                }

                // Вывод на консоль переменных и типа операции для тестирования и отладки

                if (operation == null) {
                    throw new Exception("Operation is null!");
                }

                // Проверяем тип операции и выполняем ее
                switch (operation) {
                    case PLUS -> {
                        Add add = new Add();
                        add.setNumber1(number1);
                        add.setNumber2(number2);
                        if (isRoman) {
                            romanResult = Converter.arabicToRoman(add.addNumbers());
                            break;
                        }
                        arabicResult = add.addNumbers();
                    }
                    case MINUS -> {
                        Subtract subtract = new Subtract(number1, number2);
                        if (isRoman) {
                            romanResult = Converter.arabicToRoman(subtract.subtractNumbers());
                            break;
                        }
                        arabicResult = subtract.subtractNumbers();
                    }
                    case MULTIPLY -> {
                        Multiply multiply = new Multiply();
                        multiply.setNumber1(number1);
                        multiply.setNumber2(number2);
                        if (isRoman) {
                            romanResult = Converter.arabicToRoman(multiply.multiplyNumbers());
                            break;
                        }
                        arabicResult = multiply.multiplyNumbers();
                    }
                    case DIVIDE -> {
                        Divide divide = new Divide(number1, number2);
                        if (isRoman) {
                            romanResult = Converter.arabicToRoman(divide.divideNumbers());
                            break;
                        }
                        arabicResult = divide.divideNumbers();
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + operation);
                }
                System.out.println("Output: " + (!isRoman ? arabicResult : romanResult));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}