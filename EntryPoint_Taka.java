package jp.ne.tin.calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class EntryPoint extends JFrame implements ActionListener {

	/** バージョン表記 */
	private static final long serialVersionUID = 1L;

	// =============== Fields =====================

	private JPanel contentPane;
	private JTextField textField;
	private JButton button_0;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JButton button_6;
	private JButton button_7;
	private JButton button_8;
	private JButton button_9;
	private JButton button_AC;
	private JButton button_10;
	private JButton button_11;
	private JButton button_12;
	private JButton button_13;
	private JButton button_14;

	// aは最初のに入力した数
	// bは次に入力した数
	// 明示的に修飾詞(private)を付加しましょう。
	private int a, b, c, x; // これはリテラル
	private String d; // Java特有ですが文字列はリテラルではなくクラス（特殊クラス）です。

	// ================== Constructs =====================

	/**
	 * コンストラクタ
	 */
	public EntryPoint() {

		setTitle("culculation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 5, 6, 7));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][][]", "[][][][][][]"));

		textField = new JTextField();
		contentPane.add(textField, "cell 0 0 3 1,width :500:");
		textField.setColumns(40);

		// ボタンの配置及び入力設定
		createButtons();
	}

	// ================== Methods ================================

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					EntryPoint frame = new EntryPoint();
					frame.setVisible(true);

				} catch (Exception e) {

					// このTry-Catchこそが次回に習得する例外処理です。
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * ボタンを作成し配置します。
	 * 
	 * 共通化するための関数化ではありませんが役割を独立させるためにメソッド化する方法もあります。
	 */
	private void createButtons() {

		// 20190808_今回の共通化でDesignにボタンはなくなり、論理ボタンになります。
		
		// ---------- 数字ボタン ----------------
		JButton[] btns = new JButton[] { 
				button_0
				, button_1
				, button_2
				, button_3
				, button_4
				, button_5
				, button_6
				, button_7
				, button_8
				, button_9
				};

		for (int i = 0; i < btns.length; i++) {

			JButton btn = btns[i];
			String btnTxt = String.valueOf(i);
			btn = new JButton(btnTxt);
			btn.addActionListener((ActionListener) this);
			
			String txt = "cell";
			if (i == 0) {
				
				txt += btnTxt + " 4";
				
			} else if (i < 4) {

				txt += String.valueOf(i - 1) + " 3";
				
			} else if (i < 7) {
				
				txt += String.valueOf(i - 4) + " 2";
				
			} else {	// i < 9 と同義です。
				
				txt += String.valueOf(i - 7) + " 1";
			}
			contentPane.add(btn, txt);				
		}

		// ---------- 機能ボタン ----------------
		// btnsを配列を再利用することもできます。
		btns = new JButton[] {
				button_AC
				, button_12
				, button_13
				, button_10
				, button_11
				, button_14
		};
		String[] btnTxts = new String[] {
				"AC"
				, "*"
				, "/"
				, "-"
				, "+"
				, "="
		};

		for (int i = 0; i < btns.length; i++) {
			
			JButton btn = btns[i];
			String btnTxt = btnTxts[i];
			btn = new JButton(btnTxt);
			btn.addActionListener((ActionListener) this);

			// txtはスコープが違うので再利用にはなりません。
			String txt = "cell 3 " + String.valueOf(i);
			contentPane.add(btn, txt);	
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton) e.getSource();
		String btnTxt = btn.getText();
		
		try {
			Integer.parseInt(btnTxt);	// 数字でないと例外をスローします。
			textField.setText(textField.getText() + btnTxt);
			// 数字ボタンはここまでです、ここでreturnすることで機能ボタンのロジックのネストが１階層浅くできます。
			return;

		} catch (NumberFormatException ex) {

			/*
			 * 数字ではないので機能ボタンであることは判明します。
			 * このように数字であるかを確認せず例外で振り分ける方法もあります。
			 */
		}
		
		// -------- ここからは機能ボタンしか到達しません。 --------------
		System.out.println("機能ボタンが押されました。");
		
		try {
			switch (btnTxt) {
			case "*":
				a = Integer.parseInt(textField.getText());
				x = 3;
				textField.setText("");
				break;
				
			case "/":
				a = Integer.parseInt(textField.getText());
				x = 4;
				textField.setText("");
				break;
				
			case "-":
				a = Integer.parseInt(textField.getText());
				x = 1;
				// textField内をなにも書かれていない状態にする
				textField.setText("");
			
			case "+":
				a = Integer.parseInt(textField.getText());
				x = 2;
				textField.setText("");
				break;
			
			case "=":
				
				// 次に入力した値をint型に変換後bに代入
				try {
					b = Integer.parseInt(textField.getText());
				} catch (NumberFormatException e6) {
					textField.setText("数値を入力してください");
					x=5;
				}
				// 先ほど決めたxの値をもとに計算方法を決める
				switch (x) {
				case 1:
					c = a - b;
					break;
				case 2:
					c = a + b;
					break;
				case 3:
					c = a * b;
					break;
				case 4:
					try {
						c = a / b;
					} catch (ArithmeticException e1) {
						textField.setText("０で割れません");
						x = 5;
					}
					break;
				}
				// 計算結果をString型に戻す
				if (x != 5) {
					d = String.valueOf(c);
					textField.setText(d);
				}
				break;
				
			default:	// ACです。
				
				// 値が全てリセットされるようになる
				a = 0;
				b = 0;
				textField.setText("");
				break;
			}
		} catch (NumberFormatException e2) {
		
			textField.setText("数値を入力してください");
		}
	}
}
