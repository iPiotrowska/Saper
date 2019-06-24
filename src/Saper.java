import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class Saper extends JFrame implements ActionListener
{
    int fieldSize=50;
    Random r;

    int widthMap;
    int heightMap;
    int minesMap;
    int openToWin;
    JButton[][] fields;

    Saper(int widthMap,int heightMap,int minesMap)
    {
        this.r = new Random();
        this.widthMap=widthMap;
        this.heightMap=heightMap;
        this.minesMap=minesMap;
        this.openToWin=(widthMap*heightMap)-minesMap;
        setSize(520,550);
        setTitle("Saper");
        fields = new JButton[widthMap][heightMap];
        setLayout(null);
        for(int i=0; i<widthMap;i++)
        {
            for(int j=0;j<heightMap;j++)
            {
                JButton b = new JButton();
               // b.setSize(fieldSize,fieldSize);
                b.setBounds(fieldSize*i,fieldSize*j,fieldSize,fieldSize);
                b.addActionListener(this);
                fields[i][j]=b;
                add(b);
            }
        }

        do
        {
            int x = r.nextInt(this.widthMap);
            int y = r.nextInt(this.heightMap);
            if (fields[x][y].getName() != "x")
            {
                fields[x][y].setName("x");
                minesMap--;
            }
        } while (minesMap > 0);

        for (int x = 0; x < widthMap; x++)
        {
            for (int y = 0; y < heightMap; y++)
            {
                if (fields[x][y].getName() != "x")
                {
                    int mineCount = 0;
                    for (int xx = x - 1; xx <= x + 1; xx++)
                    {
                        for (int yy = y - 1; yy <= y + 1; yy++)
                        {
                            if (xx >= 0 && xx < widthMap &&
                                    yy >= 0 && yy < heightMap &&
                                    fields[xx][yy].getName() == "x")
                            {
                                mineCount++;
                            }
                        }
                    }
                    if (mineCount > 0)
                    {
                        fields[x][y].setName(Integer.toString(mineCount));
                    }
                }
            }
        }

//        for(int i=0; i<widthMap;i++)
//        {
//            for(int j=0;j<heightMap;j++) {
//            System.out.print(fields[i][j].getName() + " ");
//            }
//            System.out.println(" ");
//            }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (b.getName() == "x")
        {
            EndGame("Niestety przegrałeś");
        }
        else
        {
            for (int x = 0; x < widthMap; x++)
            {
                for (int y = 0; y < heightMap; y++)
                {
                    if (fields[x][y] == b)
                    {
                        OpenField(x, y);
                    }
                }
            }
        }
        if (openToWin == 0)
        {
            EndGame("Brawo - wygrałeś!");
        }
    }

    private void EndGame(String info)
    {
        OpenAllFields();
        JOptionPane.showMessageDialog(null,info);
    }

    private void OpenField(int x, int y)
    {
        if (fields[x][y].getBackground() != Color.WHITE)
        {
            fields[x][y].setText(fields[x][y].getName());
            fields[x][y].setBackground(Color.WHITE);
            openToWin--;
            if (fields[x][y].getName() == null)
            {
                for (int xx = x - 1; xx <= x + 1; xx++)
                {
                    for (int yy = y - 1; yy <= y + 1; yy++)
                    {
                        if (xx >= 0 && xx < this.widthMap &&
                                yy >= 0 && yy < this.heightMap)
                        {
                            OpenField(xx, yy);
                        }
                    }
                }
            }
        }
    }

    private void OpenAllFields()
    {
        for (int x = 0; x < widthMap; x++)
        {
            for (int y = 0; y < heightMap; y++)
            {
                fields[x][y].setText(fields[x][y].getName());
                if (fields[x][y].getName() == "x")
                {
                    if (openToWin == 0)
                    {
                        fields[x][y].setBackground(Color.PINK);
                    }
                    else
                    {
                        fields[x][y].setBackground(Color.RED);
                    }
                }
                    else
                {
                    if (openToWin == 0)
                    {
                        fields[x][y].setBackground(Color.WHITE);
                    }
                    else
                    {
                        fields[x][y].setBackground(Color.PINK);
                    }
                }
            }
        }
    }
}

