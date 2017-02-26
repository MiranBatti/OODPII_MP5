package hanoi;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import command.MacroCommand;
import command.MoveCommand;
import state.AutomaticState;
import state.MacroState;
import state.ManualState;

class TowerOfHanoiApp extends JFrame
  {
  private static final long serialVersionUID = 1L;
  private HanoiModel        hanoiModel       = new HanoiModel();
  private MoveCommand		command			 = new MoveCommand(hanoiModel);
  private MacroCommand		macroCommand	 = new MacroCommand();
  private Mediator 			mediator		 = new Mediator(hanoiModel, command, macroCommand);
  private RodPanel          rodPanelA        = new RodPanel(hanoiModel.getIterableRod(0), mediator);
  private RodPanel          rodPanelB        = new RodPanel(hanoiModel.getIterableRod(1), mediator);
  private RodPanel          rodPanelC        = new RodPanel(hanoiModel.getIterableRod(2), mediator);
  private HanoiSolver       hanoiSolver;
  private boolean			macroClicked	= false;
  private JPanel southP;
  private JLabel mode;

  public TowerOfHanoiApp()
    {
    super("Tower of Hanoi");
    hanoiModel.addObserver(0, rodPanelA);
    hanoiModel.addObserver(1, rodPanelB);
    hanoiModel.addObserver(2, rodPanelC);
    rodPanelA.setIndex(0);
    rodPanelB.setIndex(1);
    rodPanelC.setIndex(2);
    setJMenuBar(makeMenuBar());
    JPanel centerP = makeCenterPanel();
    southP = makeSouthPanel();
    Container c = getContentPane();
    c.setBackground(Color.black);
    c.setLayout(new BorderLayout());
    c.add(centerP, BorderLayout.CENTER);
    c.add(southP, BorderLayout.SOUTH);
    hanoiSolver = new HanoiSolverRecursiveImpl(hanoiModel);
    hanoiModel.reset();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);
    setVisible(true);
    }

  private void stop()
    {
    hanoiSolver.halt();
    }

  private int inputIntegerDialog(String message)
    {
    int result = 0;
    String string = JOptionPane.showInputDialog(this, message);
    try
      {
      result = Integer.parseInt(string);
      }
    catch (Exception e)
      {
      }
    return result;
    }

  private void changeNumberOfRings()
    {
    int disks = inputIntegerDialog("<html>Current number of disks = " + hanoiModel.getNumberOfDisks() + ".<br>Enter new number of disks:</html>");
    if (disks > 0)
      {
      hanoiSolver.halt();
      hanoiModel.setNumberOfDisks(disks);
      }
    }

  private void changeSpeed()
    {
    int delayTime = inputIntegerDialog("<html>Current delay = " + hanoiSolver.getDelayTime() + " ms.<br>Enter new delay:</html>");
    if (delayTime >= 0)
      {
      hanoiSolver.setDelayTime(delayTime);
      }
    }

  private void start()
    {
	  mediator.setState(AutomaticState.getInstance());
    new Thread() {
    public void run()
      {
      try
        {
        hanoiSolver.solve();
        }
      catch (Exception e)
        {
        hanoiSolver.halt();
        hanoiModel.reset();
        }
      java.awt.Toolkit.getDefaultToolkit().beep();
      }
    }.start();
    }

  private void quit()
    {
    System.exit(0);
    }
  
  private void undo()
  {
	  mediator.undoRequest();
  }
  private void redo()
  {
	  mediator.redoRequest();
  }
  private void macro()
  {
	  mediator.macro();
	  if(!macroClicked)
		  mediator.setState(MacroState.getInstance(hanoiModel, command, macroCommand));
	  else
		  mediator.setState(ManualState.getInstance(hanoiModel, command));
  }
  
  private JMenuBar makeMenuBar()
    {
    JMenuItem ringsMI = new JMenuItem("Set rings");
    JMenuItem speedMI = new JMenuItem("Set speed");
    JMenuItem manualMode = new JMenuItem("Manual mode");
    JMenu menu = new JMenu("Menu");
    menu.add(ringsMI);
    menu.add(speedMI);
    menu.add(manualMode);
    JMenuBar bar = new JMenuBar();
    bar.add(menu);
    ringsMI.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ae)
      {
      changeNumberOfRings();
      }
    });
    speedMI.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ae)
      {
      changeSpeed();
      }
    });
    manualMode.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			stop();			
			mediator.setState(ManualState.getInstance(hanoiModel, command));
			southP.getComponent(3).setEnabled(true);	//undoB
			southP.getComponent(4).setEnabled(true);	//redoB
			southP.getComponent(5).setEnabled(true);	//macroB
			southP.getComponent(0).setEnabled(false);	//startB
			southP.getComponent(1).setEnabled(false);	//stopB
			mode.setText("Mode: Manual");
		}
	});
    return bar;
    }

  private JPanel makeCenterPanel()
    {
    JPanel centerP = new JPanel();
    mode = new JLabel("Mode: Automatic");    
    centerP.setLayout(new GridLayout(3, 7));
    centerP.setBackground(Color.black);
    for (int i = 0; i < 7; i++)
      centerP.add(new JLabel(""));
    centerP.add(new JLabel(""));
    centerP.add(rodPanelA);
    centerP.add(new JLabel(""));
    centerP.add(rodPanelB);
    centerP.add(new JLabel(""));
    centerP.add(rodPanelC);
    for (int i = 0; i < 7; i++)
     centerP.add(new JLabel(""));
    mode.setForeground(Color.yellow);
    centerP.add(mode);
    return centerP;
    }

  private JPanel makeSouthPanel()
    {
    JButton startB = new JButton("Start");
    JButton stopB = new JButton("Stop");
    JButton quitB = new JButton("Quit");
    JButton undoB = new JButton("Undo");
    JButton redoB = new JButton("Redo");
    JButton macroB = new JButton("Macro");
    JButton resetB = new JButton("Reset");
    startB.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ae)
      {
      start();
      }
    });
    stopB.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ae)
      {
      stop();
      }
    });
    quitB.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ae)
      {
      quit();
      }
    });
    undoB.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			undo();
		}
	});
    redoB.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			redo();
		}
	});
    macroB.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			macro();
			macroClicked = !macroClicked;
			if(macroClicked)
			{
				macroB.setText("Finish");
				mode.setText("Mode: Macro");
			}
			else
			{
				macroB.setText("Macro");
				mode.setText("Mode: Manual");
			}
			
		}
	});
    resetB.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			hanoiModel.reset();
			mediator.setState(AutomaticState.getInstance());
			mediator.resetRequest();
		    undoB.setEnabled(false);
		    redoB.setEnabled(false);
		    macroB.setEnabled(false);
		    startB.setEnabled(true);
		    stopB.setEnabled(true);
		    mode.setText("Mode: Automatic");
		}
	});
    undoB.setEnabled(false);
    redoB.setEnabled(false);
    macroB.setEnabled(false);
    JPanel southP = new JPanel();
    southP.add(startB);
    southP.add(stopB);
    southP.add(quitB);
    southP.add(undoB);
    southP.add(redoB);
    southP.add(macroB);
    southP.add(resetB);
    return southP;
    }

  public static void main(String args[])
    {
    new TowerOfHanoiApp();
    }
  }
