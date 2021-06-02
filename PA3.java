/*******************************************************************************
 ****    SENG2200 Assignment 3
 ****    c3308061
 ****    Lachlan Court
 ****    03/06/2021
 ****    This class is the starting point of the program
 *******************************************************************************/
public class PA3
{
    // Instance variables to be read from file arguments
    private double M;
    private double N;
    private int Qmax;

    // Main function
    public static void main(String[] args)
    {
        // There should be either 3 or 4 arguments. Terminate if there is an invalid number
        if (args.length < 3 || args.length > 4)
        {
            System.err.println("Invalid input data. Terminating...");
            System.exit(-1);
        }
        // If there is a suitable number of arguments, pass those arguments to the run() method
        PA3 assign = new PA3();
        assign.run(args);
    }

    public void run(String[] args)
    {
        // Set up values from arguments
        M = Double.valueOf(args[0]); // Average processing time
        N = Double.valueOf(args[1]); // Range of processing time
        Qmax = Integer.valueOf(args[2]); // Capacity of storage queues
        // Assume there is no filename
        String filename = null;
        // If there is a 4th argument, iterpret it as the filename
        if(args.length == 4)
        {
            filename = args[3];
        }

        // This is where the magic happens. Create a new production line, passing it the interpreted arguments
        ProductionLine line = new ProductionLine(M, N, Qmax, filename);
        // Ask the line to produce(), that is, to run a simulation
        line.produce();
        // Ask the line to report(), that is to output a summary of the simulation data
        System.out.println(line.report());
    }
}

/*
Below are some Cats. Enjoy!

MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxdx0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMW0:...;oKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMXk:,;'';;cdKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXXW
MMMMMMMMMMMMMMMMMMMMMMMMMMWd;oKXd';k0ocx0XMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKOxl;lX
MMMMMMMMMMMMMMMMMMMMMMMMMNkcxNMMKc'oNNd,':kNMMMMMMMMMMMMMMMMMMMMMMMMMNKkl;,;l:,x
MMMMMMMMMMMMMMMMMMMMMMMMNd,dNMMMWKc:0MNOdc,dXNNNNNNNNNNWMMMMMMMMMMW0dl:cxkc'co:o
MMMMMMMMMMMMMMMMMMMMMMMWx,lKWWMWWNd'dNMMMKc';;;;;;;;;;:lxk0NWWMMW0o,:x00d;coOKl,
MMMMMMMMMMMMMMMMMMMMMMWk,,c::oko:l:.:KMWWXl..;xk:...,dc...:l::x0o;cOXKo;:xXWMWo;
MMMMMMMMMMMMMMMMMMMMMWk,.cko::,,::::xNMWWNxclONXl..,dOc..c0k;.',:kXNx;,oKWMMMWk;
MMMMMMMMMMMMMMMMMMMXkl:lxKWWWNKKNWWWWMMMMMWWWMMW0kk0WKo;l00c..'c0NOc:l0WMMMMMMKc
MMMMMMMMMMMMMMMMWNk:'cONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNNWWk::oONNk,.,lookNMMMMK:
MMMMMMMMMMMMMMWNOc:lkNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNXNWMMWOolooc''l0NMMK;
MMMMMMMMMMMMMKxlcxXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNo..'xWMK:
MMMMMMMMMMMWKl,oXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXkc.,d0Oc
MMMMMMMMMMXx;,xNMMMMMMMNKOO0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0ookkx:
MMMMMMMMMXl,l0WMMMMMMMW0c'.'dNMMMMWWWMMMMMMWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWMMXd
MMMMMMMMXo;dNMMMMMMMMMMNOc,cOWWWWMMMMMMMMMWNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN
MMMMMMMNdckNMMMMMMMMMMMMMNXXWMXxxXWNNWMMMMWWWMWMMW0kxKWMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMWKocOWMMMMMMMMMMMMMMMMMMMNo,;:::oKWWNxcxNMMWk,..cKWMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMKl;xNMMMMMMMMMMMMMMMMMMMMMXOOOOkooxkxockWMMMKoccxNMMMMMMMMMMMMMMMMMMMMMMMMM
x0XWXl,dXMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWMMMMMWWWWMMMMMMMMMMMMMMMMMMMMMMMMMM
xolo::OWMMMMMMMMMWNNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MNKd:kWMMMMMMMMMNk::kXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMWklKMMMMMMMWKxlcllolcoxxxk0NWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
WMWkcdKWWWWKOxc;dXWMWX0OdcccoxocdkOOKWWWWMMMMMMMMMMMMMMMWKKNMMMMMMMMMMMMMMMMMMMM
MMMNkccccccccoOXWMMMMMMMMMMWWNKOko:cokxdoxO0000XMMMMMWXOdcoXMMMMMMMMMMMMMMMMMMMM
MMMMWN0kxxkKNWMMMMMMMMMMMMMMMMMMMWWWWNX0kko::::ldox00dc:o0WMMMMMMMMMMMMMMMMMXkOW
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNNNNXOxdxl,oXWMMMMMMMMMMMMMMMMWKkc;dN
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNx;ckNMMMMMMMMMMMMMMWNXOl;cxKNN
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNWWMMMMMW0:;0MMMMMMMMMMMMMWW0o:;',ckXXXK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWMMMMMMWO;:0MMMMMMMMWNKOdcoodOOdll::;:;
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXdcllx00xlcloxookkKWWMMWWWKkkkx
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0xxkkxxxx0NNWMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM

MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOdONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMW0l..'c0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMW0l:,..;:oONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWMK
MMMMMMMMMMMMMMMMMMMMMMMMMMWk;cKKl:dOocxXWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0OOdOWK
MMMMMMMMMMMMMMMMMMMMMMMMMNOccKWW0c:ON0oclxXMMMMMMMMMMMMMMMMMMMMMMMMMMN0xc::;,:OK
MMMMMMMMMMMMMMMMMMMMMMMMNd'c0MMMWO:cKMNd,,oNMNXNNWMMMMMMMMMMMMMMMMNKxc:lxkdoo:d0
MMMMMMMMMMMMMMMMMMMMMMMMKc:ONWMMMNo:OWMX0o,cdl:lccdddxxOXNNWMMMWNOl:lkOxc;cxx:cx
MMMMMMMMMMMMMMMMMMMMMMMKl:dol0X0xx:'oNMWNO;..l0x,...;l,',;:okKNx:cxKKd;,:kNMK:;d
MMMMMMMMMMMMMMMMMMMMMMKc.cd;',;,,,.;kWMWW0:.cKNd'..c0d'.,xOd,,;:xXN0ocd0XMMMNxox
MMMMMWX000XWMMMMMMMWXd;,oKWXOOOOKKOKWMMMMWXOXWWKddOXXl.,xKk:..,xXNx;lKWMMMMMWOcl
MMMXxc::::cclkXMMWOlcox0WMMMMMMMMMMMMMMMMMMMMMMMWWMMWKk0N0l;lx0NKo'.,clxXMMMMKc;
MMNo;ckKNXOo:':ooo:cONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNNWMMWKl,,::''dXNMMXl,
MMK::0WWWWWWXkl;..lXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNXXNKc'',xWMXl.
MMK;;KMMMWWMMMWXd,:ONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOc.:x00c'
MMK::KMMMMMMWWMMWx,;OMMN0OkOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWkc:cxx:;
MMNo:ONWMMMMWWMMMWKOXMM0c...oXWMMMMMMMMMMMWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWKl'
MMWd'oXWWMMMWWMMMMMMMMMNd;;l0NWNNMMMMMMMMMWWWWMMMMMMMMMMMMNK00kkKKNMMMMMMMMMMMNd
MMW0::0WWNNNWMMMMMMMMMMMWNNNWMXdxXNXNWMMMMWNWWWMWXOOKNMMWXo;;;,,;;lkXWMMMMMMMMMK
MMMWOcdNMMWMMMMMMMMMMMMMMMMMMMNd;;:;;l0NNXdoXMMMXl..,dNMXo,lOKXKK0xc;l0WMMMMMMMK
MMMMNo,dXMMMMMMMMMMMMMMMMMMMMMMNK000xlc::lokNMMMNxlllOWW0cc0WMMMWNN0d;,oKWMMMMMK
dO0XWO;:OXWMMMMMMMMMMMMMMMMMMMMMMMMMMWX00XWMMMMMMMWMWMMWO:oNWWNNNWMWNKd,;dXWMMMK
kkkxkd;.;:clx0KXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM0:cKWNNWWNNNWMWO:.cXMMMK
MMWNNXOxddddl::lolookKXNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMK:'xNWMMMWNWMMMW0c;oXMMK
MMMMMMMMMMMMNXXKkooodxooxxddOXNNWMMMMMMMMMMMMMMMMMMMMMMMNd'cXWMMMMWWMMMMMNo,oNMK
MMMMMMMMMMMMMMMMMMMMMWXK0xlldxocokkxx0NWWWWMMMMMMMMMMMMMM0:,xXNWNWNWMMMMMWXxxXMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX00OdccoxoccdkOkkOXWWMMMMMMXo'cKWNNWWMMMMMMMWWMMMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWKOOkoc:::cclxO0000Kk:;xNMMMMMMMMMMMMMMMMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWN0kxl::::clo:.;kKNMMMMMMMMMMMMMMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNNNN0xxl,,;:ldkKXXXNWMMMMMMK
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXXXXKkdl:;;:oxOXNNN0
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKKKKOxdl:;:;
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0OOx
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMK
 */