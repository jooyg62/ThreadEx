import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyFrame extends JFrame {
	private JLabel label = new JLabel();
	private JLabel label2 = new JLabel();
	private JTextField tf = new JTextField(5);
	MyThread th;
	MyThread th2;

	public MyFrame() {
		setTitle("나의 프레임");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		c.add(tf);
		tf.setFont(new Font("Gothic", Font.ITALIC, 60));
		tf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField t = (JTextField) e.getSource();
				String s = t.getText();
				int n = 0;
				try {
					n = Integer.parseInt(s);
				} catch (NumberFormatException ex) {
					t.setText("숫자만 쳐!!");
					return;
				}
				th.setNumber(n);
			}

		});

		label.setFont(new Font("Gothic", Font.ITALIC, 60));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(Color.YELLOW);
		label.setOpaque(true);
		c.add(label);

		label2.setFont(new Font("Consolas", Font.ITALIC, 60));
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setBackground(Color.GREEN);
		label2.setOpaque(true);
		c.add(label2);

		setSize(300, 300);
		setVisible(true);// event dispatch Thread // 여기서 쓰레드가 하나 더생김

		c.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				th.interrupt();
			}
		});

		th2 = new MyThread(label2, 200, null);
		th2.start();

		th = new MyThread(label, 100, th2);
		th.start();
		
		Thread th3 = new Thread(new ColorThread(label));
		th3.start();

	}

	class ColorThread implements Runnable {
		private JLabel label;
		
		public ColorThread(JLabel label){
			this.label = label;
			
		}

		@Override
		public void run() {
			while(true) {
				int r = (int) (Math.random()*256);
				int g = (int) (Math.random()*256);
				int b = (int) (Math.random()*256);
				Color c = new Color(r,g,b);
				label.setForeground(c);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
	}
	
	class MyThread extends Thread {
		private JLabel label;
		private long delay;
		private Thread next;
		private int n;

		public MyThread(JLabel label, long delay, Thread next) {
			this.label = label;
			this.delay = delay;
			this.next = next;
			setNumber(0);
		}

		public void setNumber(int n) {
			this.n = n;
		}

		public void run() {
			while (true) {
				label.setText(Integer.toString(n));
				try {
					sleep(delay);
					n++;
				} catch (InterruptedException e) {
					label.setText(label.getText() + "후 killed");
					if (next != null) {
						next.interrupt();
					}
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		Thread m = Thread.currentThread();
		int n = Thread.activeCount();
		System.out.println(n);
		System.out.println(m.getName());
		System.out.println(m.getId());
		System.out.println(m.getPriority());
		System.out.println(m.getState());

		MyFrame f = new MyFrame();
		// n = Thread.activeCount(); // 값은 4 , why?
	}

}
