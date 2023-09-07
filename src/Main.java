import java.util.*;

public class Main {
    public static void main(String[] args) { //Utilizar StringTokenizer

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Ingresa la expresión para convertirla de infija a sufija: ");
            String expresion = scanner.nextLine();

            if(expresion.equals("quit")){
                break;
            }
            StringTokenizer st = new StringTokenizer(expresion,
                    "()[]{}+-*/",
                    true);
            Stack<String> stack = new Stack<>();
            while (st.hasMoreTokens()){
                String token = st.nextToken();
                switch (token) {
                    case "(":
                    case "[":
                    case "{":
                        stack.push(token);
                        break;
                    case ")":
                    case "]":
                    case "}":
                        if (stack.empty()){
                            System.out.println("Error en la expresión");
                        }else {
                            stack.pop();
                        }
                        break;
                }

            }
            String expresionSufija = convertirInfijaASufija(expresion);
            if (expresionSufija != null) {
                System.out.println("Expresión sufija: " + expresionSufija);
            } else {
                System.out.println("Error en la expresión");
            }
            break;
        }
    }

    public static String convertirInfijaASufija(String expresion) {
        StringTokenizer st = new StringTokenizer(expresion, "()[]{}+-*/", true);
        Stack<String> operadores = new Stack<>();
        Queue<String> salida = new LinkedList<>();

        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            if (esNumero(token)) {
                salida.add(token);
            } else if (esOperador(token)) {
                while (!operadores.isEmpty() && esOperador(operadores.peek()) &&
                        prioridad(token) <= prioridad(operadores.peek())) {
                    salida.add(operadores.pop());
                    }
                    operadores.push(token);
                } else if (token.equals("(")) {
                    operadores.push(token);
                } else if (token.equals(")")) {
                    while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                        salida.add(operadores.pop());
                    }
                    if (!operadores.isEmpty() && operadores.peek().equals("(")) {
                        operadores.pop();
                    } else {
                        return null; // Error en la expresión
                    }
                }
            }

        while (!operadores.isEmpty()) {
            if (operadores.peek().equals("(") || operadores.peek().equals(")")) {
                return null; // Error en la expresión
            }
            salida.add(operadores.pop());
        }
        StringBuilder expresionSufija = new StringBuilder();
        for (String elemento : salida) {
            expresionSufija.append(elemento).append(" ");
        }

        return expresionSufija.toString().trim();
    }

        public static boolean esNumero(String token) {
            try {
                Double.parseDouble(token);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean esOperador(String token) {
            return "+-*/".contains(token);
        }

        public static int prioridad(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
}


