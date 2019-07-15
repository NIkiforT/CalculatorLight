import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class CalculatorFrame extends JFrame {
    //конструктор класса:
    CalculatorFrame(){
        int w = 270, h = 240;
        setTitle("Калькулятор");
        //Установка размера и положение поля:
        setBounds(100, 100, w, h);
        //создание панели с кнопками и полем:
        CPanel panel = new CPanel(w, h);

        //добавим панель в окно
        add(panel);
        setResizable(false); //запрет на изменение размера окна.
        setVisible(true);
        // закрытие окна по щелчку
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    //Класс панели
class CPanel extends JPanel{
    //текстовое поле
    public JTextField TF;
    //обработчик щелчка по кнопке
    private BtnAction BtnPressed;
    //конструктор класса:
    CPanel(int W, int H){
        // Размеры кнопок и отступы:
        int w = W/5, h = H/8, sx = w/5, sy = h/3;

        setLayout(null);
        setBounds(0,0,W,H);
        JTextField TF =new JTextField();
        TF.setHorizontalAlignment(JTextField.RIGHT);
        TF.setBounds(sx,sy,2 * sx + 3 * w, h);
        // Отмена возможности редактирования поля:
        TF.setEnabled(false);
        add(TF);


        // Создание обработчика щелчка на кнопке:
        BtnPressed = new BtnAction(TF);
        // Список названий кнопок:
        String[] BtnTxt ={"1","2","3","+","4","5","6","-","7","8","9","/","0",".","=","*"};
        // Создание кнопок и добавление их на панель:
        for(int i=0;i<BtnTxt.length;i++){
            addBtn(sx+(w+sx)*(i%4),(2*sy+h)+(sy+h)*(i/4),w,h,BtnTxt[i],BtnPressed);}

        // Создание кнопки сброса параметров:
        JButton BtnC=new JButton("C");
        // Размер и положение кнопки:
        BtnC.setBounds(4*sx+3*w,sy,w,h);
        // Добавление обработчика для кнопки:
        BtnC.addActionListener(BtnPressed);
        // Режим отсутствия выделения названия кнопки при активации:
        BtnC.setFocusPainted(false);
        // Красный цвет для названия кнопки:
        BtnC.setForeground(Color.RED);
        // Добавление кнопки на панель:
        add(BtnC);
    }

// Метод для создания и добавления кнопок
// (аргументы - положение и размер кнопки, название и обработчик щелчка):
    void addBtn(int i, int j, int w, int h, String txt, ActionListener AcList) {
        JButton b = new JButton(txt);
        b.setBounds(i, j, w, h);
        // Режим отсутствия выделения названия кнопки при активации:
        b.setFocusPainted(false);
        // Добавление обработчика для кнопки:
        b.addActionListener(AcList);
        add(b);
    }
}
// Класс обработчика щелчка на кнопке:
class BtnAction implements ActionListener{
    public JTextField TF;
    private boolean start; // Индикатор состояния ввода числа
    private boolean point; // Индикатор состояния ввода десятичной точки


    // Текстовое представление последнего введенного оператора:
    private String cmnd;
    // Поле для записи промежуточного результата:
    private double result;
    // Метод для сброса параметров:
    private void onStart(){
        start=true;
        point=true;
        cmnd="C";
        result=0;
        TF.setText("0.0");}
    // Метод для вычисления результата последней операции:
    private void calc(){
// Введенное в поле число:
        double x;
        x=Double.parseDouble(TF.getText());
// Вычисление результата:
        if(cmnd.equals("*")) result*=x;
        else if(cmnd.equals("/")) result/=x;
        else if(cmnd.equals("-")) result-=x;
        else if(cmnd.equals("+")) result+=x;
        else result=x;
        // Заполнение текстового поля:
        TF.setText(Double.toString(result));}
    // Конструктор класса (аргумент - текстовое поле):
    BtnAction(JTextField TF){
        this.TF = TF;
        onStart();
    }
    // Реакция на щелчок на кнопке:
    public void actionPerformed (ActionEvent ae){
    // Считывание текста на кнопке:
        String str=ae.getActionCommand();
        // Проверка вариантов:
        if(str.equals("C")){// Кнопка сброса значений
            onStart();
            return;
        }

        // Вычисление результата:
        if(str.equals("+")|str.equals("-")|str.equals("*")|str.equals("/")|str.equals("=")){
            calc();
            cmnd=str;
            start=true;
            point=true;
            return;
        }
        // Ввод числа:
        if(start){
            if(str.equals(".")){
                TF.setText("0.");
                point=false;
                start=false;
                return;}
            else{// Ввод цифры в начале ввода числа
                TF.setText(str);
                start=false;
                return;
            }
        }else {
            if(str.equals(".")){// Попытка ввести точку
                str=point?str:"";
                point=false;
        }
            // Добавление цифры к числу:
            // Незначащий первый ноль:
            if(TF.getText().equals("0")&!str.equals(".")) TF.setText(str);
            else TF.setText(TF.getText()+str);}
    }}

// Класс с главным методом программы:
class MyCalculator {
    public static void main(String[] args) {
// Создание окна:
        new CalculatorFrame();
    }
}





