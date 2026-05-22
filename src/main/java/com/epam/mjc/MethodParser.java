package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String[] parts = signatureString.split("\\(");

        String beforeBracket = parts[0].trim();
        String argumentsPart = parts[1].replace(")", "").trim();

        String[] words = beforeBracket.split("\\s+");

        String accessModifier = null;
        String returnType;
        String methodName;

        int index = 0;

        if (words.length == 3 || words.length == 4) {
            String first = words[0];
            if (first.equals("public") || first.equals("private") || first.equals("protected")) {
                accessModifier = first;
                index = 1;
            }
        }

        returnType = words[index];
        methodName = words[index + 1];

        List<MethodSignature.Argument> args = new ArrayList<>();

        if (!argumentsPart.isEmpty()) {
            String[] arguments = argumentsPart.split(",");

            for (String arg : arguments) {
                String[] argumentParts = arg.trim().split(" ");

                args.add(new MethodSignature.Argument(argumentParts[0], argumentParts[1]));
            }
        }


        MethodSignature method = new MethodSignature(methodName, args);
        method.setReturnType(returnType);
        method.setAccessModifier(accessModifier);

        return method;
    }
}
