package Delegation;

public class test2 {
    public static final String MESSAGE_TO_PRINT = "hello world";
    /**
     *Printer
     */
    public static void main(String[] args) {
        PrinterController hpPrinterController = new PrinterController(new HpPrinter());
        PrinterController canonPrinterController = new PrinterController(new CanonPrinter());
        PrinterController epsonPrinterController = new PrinterController(new EpsonPrinter());

        hpPrinterController.print(MESSAGE_TO_PRINT);
        canonPrinterController.print(MESSAGE_TO_PRINT);
        epsonPrinterController.print(MESSAGE_TO_PRINT);
    }

    public static class PrinterController implements Printer {
        private final Printer printer;

        public PrinterController(Printer printer) {
            this.printer = printer;
        }
        @Override
        public void print(String message) {
            printer.print(message);
        }
    }

    public static class HpPrinter implements Printer {
        @Override
        public void print(String message) {
            System.out.println("Hp: " + message);
        }
    }

    public static class EpsonPrinter implements Printer {
        @Override
        public void print(String message) {
            System.out.println("Ep: " + message);
        }
    }

    public static class CanonPrinter implements Printer {
        @Override
        public void print(String message) {
            System.out.println("Cp: " + message);
        }
    }

    public interface Printer {
        void print(final String message);
    }
}
