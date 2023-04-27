package Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Services.ManagerService;
import Services.KozmeticService;
import Services.RecepcionistService;

import javax.swing.JDialog;

import java.awt.Window;

import Users.Admin;
import Users.Gosti;
import Users.Recepcioner;
import Users.Sobarica;
import net.miginfocom.swing.MigLayout;

public class Logged_User_frame {
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton btn_dodaj_gosta;
	private JButton btn_check_in;
	private JButton btn_lista_gostiju;
	private JButton btn_dodaj_rez;
	private JButton btn_pregled_rez;
	private JButton btn_potvrdjene_rez;
	private JButton btn_pregled_soba;
	private JButton btn_check_out;
	private JButton btn_sobe_za_ciscenje;
	private JButton odjava;
	
//	private String user;
//	private String pozicija;
	
	public Logged_User_frame(final String username, String position) throws IOException {
		frame = new JFrame();
		panel = new JPanel(new MigLayout("wrap, insets 20, fill", "[right]20[center]", "[center]"));//arg 2 broj col, arg 3 br redova
		
		frame.setSize(650, 500);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    final int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		
	    //CLOSING EVENT
	    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		int opt = JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			e.getWindow().dispose();
	    			Window[] children = e.getWindow().getWindows();
	                for (Window win : children) {	                	
	                	win.dispose();
	                }
	    		}
	    	}
	    });	    
	    
		frame.add(panel);
	    
		odjava = new JButton("Odjava");
		
		//DEO KOJI TREBA DA RADI
		if(position.equals("m")) {
			frame.setTitle("Adnimistrator manager");
			
			//OPTION MENU ZA ADMINA
			JMenuBar mainMenuAdmin = new JMenuBar();

			JMenu zaposleniMenu = new JMenu("Meni za zaposlene");
			JMenuItem dodajZaposlenogItem = new JMenuItem("Dodaj zaposlenog");

			zaposleniMenu.add(dodajZaposlenogItem);

			mainMenuAdmin.add(zaposleniMenu);
			
			JMenu prikazEntitetaMenu = new JMenu("Meni za prikaz entiteta");
			JMenuItem prikazEntitetaItem = new JMenuItem("Prikazi tabelu entiteta");
			JMenuItem prikazCenaItem = new JMenuItem("Prikazi tabelu cena");			
			prikazEntitetaMenu.add(prikazEntitetaItem);
			prikazEntitetaMenu.add(prikazCenaItem);
			mainMenuAdmin.add(prikazEntitetaMenu);
			
			JMenu prikazPrihodaRashodaMenu = new JMenu("Meni za razne prikaze");
			JMenuItem prikazPrihodRashodaItem = new JMenuItem("Tabela prihoda i rashoda");
			JMenuItem prikazSpremljenihItem = new JMenuItem("Tabela spremljenih soba");
			JMenuItem broj_odobrenih_rezervacijaItem = new JMenuItem("Tabela sa brojem odobrenih rezervacija");
			JMenuItem sobe_datumi_Item = new JMenuItem("Tabela prihoda soba");
			JMenuItem grafovi_Item = new JMenuItem("Prikaz grafova");			
			prikazPrihodaRashodaMenu.add(prikazPrihodRashodaItem);
			prikazPrihodaRashodaMenu.add(prikazSpremljenihItem);
			prikazPrihodaRashodaMenu.add(broj_odobrenih_rezervacijaItem);
			prikazPrihodaRashodaMenu.add(sobe_datumi_Item);
			prikazPrihodaRashodaMenu.add(grafovi_Item);
			mainMenuAdmin.add(prikazPrihodaRashodaMenu);
			
			frame.setJMenuBar(mainMenuAdmin);
			
			//IZBRISATI OVE 3 LINIJE
			JLabel reminder = new JLabel("Username: " + username + "       Pozicija: admin");
			panel.add(reminder);
			final Admin admin = new Admin();
			final ManagerService managerService = new ManagerService();
			//DODAVANJE ENTITETA
			
			dodajZaposlenogItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   //admin.DodajZaposlenog();
		        	   managerService.AddEmployee();
		           }
		    	});
			
			//IZLISTAVANJE ENTITETA
			prikazEntitetaItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   //admin.PrikazSvihEndtiteta();
					   managerService.AllEntitiesTableView();
		           }
		    	});
			
			prikazCenaItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
		        		    admin.View_Cenovink();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			
			prikazPrihodRashodaItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
		        		    admin.Prihod_Rashod();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		           }
		    	});
			prikazSpremljenihItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
		        		    admin.Spremljene_Sobe();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			broj_odobrenih_rezervacijaItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
		        		    admin.Broj_odobrenih_rezervacija();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});	
			sobe_datumi_Item.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
		        		    admin.Sobe_Datumi();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			grafovi_Item.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						admin.Prikaz_Grafova();
					} catch (IOException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		           }
		    	});
			
			panel.add(odjava, BorderLayout.SOUTH);
		}
		else if(position.equals("r")) {
			frame.setTitle("Recepcionist manager");
			
			//OPTION MENU ZA ADMINA
			JMenuBar mainMenuRecepcioner = new JMenuBar();

			JMenu gostiMenu = new JMenu("Gosti");
			JMenu sobeMenu = new JMenu("Sobe");
			JMenu rezervacijeMenu = new JMenu("Rezervacije");

			JMenuItem dodajGostaItem = new JMenuItem("Dodaj gosta");
			JMenuItem regulisiRezervacijeItem = new JMenuItem("Reguliši rezervacije");
			JMenuItem listaGostijuItem = new JMenuItem("Lista gostiju");
			JMenuItem pregledRezervacijaItem = new JMenuItem("Pregled rezervacija");
			JMenuItem potvrdjeneRezervacijeItem = new JMenuItem("Potvrđene rezervacije");
			JMenuItem pregledSobaItem = new JMenuItem("Pregled soba");
			JMenuItem zauzeteSobeItem = new JMenuItem("Zauzete sobe");

			gostiMenu.add(dodajGostaItem);
			gostiMenu.add(listaGostijuItem);
			mainMenuRecepcioner.add(gostiMenu);

			sobeMenu.add(pregledSobaItem);
			sobeMenu.add(zauzeteSobeItem);			
			mainMenuRecepcioner.add(sobeMenu);

			rezervacijeMenu.add(regulisiRezervacijeItem);
			rezervacijeMenu.add(pregledRezervacijaItem);
			rezervacijeMenu.add(potvrdjeneRezervacijeItem);			
			mainMenuRecepcioner.add(rezervacijeMenu);
			
			frame.setJMenuBar(mainMenuRecepcioner);
			
			final Recepcioner recepcioner = new Recepcioner();
			final RecepcionistService recepcionistService = new RecepcionistService();
			
			//btn_dodaj_gosta = new JButton("Dodaj gosta");
			dodajGostaItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   //recepcioner.RegisterGuest();
		        	   recepcionistService.RegisterClient();
		           }
		    	});
			//panel.add(btn_dodaj_gosta);
			
			//ne treba da bude button? msm da treba samo da se pozove nakon registracije?
			//btn_check_in = new JButton("Regulisi rezervacije");
			regulisiRezervacijeItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {
		        	   try {
						//recepcioner.RegulisiRezervaciju();
						recepcionistService.RegulateReservation();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		           }
		    	});
			//panel.add(btn_check_in);
			
			//btn_lista_gostiju = new JButton("Lista gostiju");
			listaGostijuItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						recepcioner.Guest_List();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			//panel.add(btn_lista_gostiju);
			
			//btn_pregled_rez = new JButton("Pregled rezervacija");
			pregledRezervacijaItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						recepcioner.Pregled_Rezervacija();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			//panel.add(btn_pregled_rez);
			
			//btn_potvrdjene_rez = new JButton("Potvrdjene rezervacije");
			potvrdjeneRezervacijeItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						recepcioner.Check_In();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			//panel.add(btn_potvrdjene_rez);
			
			//btn_pregled_soba = new JButton("Pregled soba");
			pregledSobaItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						recepcioner.Pregled_Soba();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			//panel.add(btn_pregled_soba);
			
			//btn_check_out = new JButton("Zauzete sobe");
			zauzeteSobeItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						recepcioner.Check_Out();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		           }
		    	});
			//panel.add(btn_check_out);
			
			JLabel recepcioner_info = new JLabel("Username: " + username + "       Pozicija: recepcioner");
			panel.add(recepcioner_info);
			
			panel.add(odjava, BorderLayout.SOUTH);
		}
		else if(position.equals("k")) {
			frame.setTitle("Kozmetic manager");
			//final Sobarica sobarica = new Sobarica();
			final KozmeticService kozmeticService = new KozmeticService(username);
			
			JMenuBar mainMenuSobarica = new JMenuBar();
			
			JMenu sobeMenu = new JMenu("Tretmani");

			JMenuItem treatmentOverview = new JMenuItem("Pregled tretmana");
			JMenuItem personalSchedule = new JMenuItem("Raspored");

			sobeMenu.add(treatmentOverview);
			sobeMenu.add(personalSchedule);
			mainMenuSobarica.add(sobeMenu);
			
			frame.setJMenuBar(mainMenuSobarica);
			
			treatmentOverview.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {			        	 
		        	   try {
						//sobarica.Sobe_Za_Ciscenje(username);
						kozmeticService.KozmeticTreatments();
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		           }
		    	});
			
			personalSchedule.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {			        	 
		        	   try {
						kozmeticService.DisplaySchedule();
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		           }
		    	});
			//panel.add(btn_sobe_za_ciscenje);
			JLabel recepcioner_info = new JLabel("Username: " + username + "       Pozicija: kozmeticar");
			panel.add(recepcioner_info);
			
			panel.add(odjava, BorderLayout.SOUTH);
		}
		else {
			frame.setTitle("Client manager");
			
			JMenuBar mainMenuRecepcioner = new JMenuBar();
			
			JMenu rezervacijeMenu = new JMenu("Rezervacije");

			JMenuItem rezervisiItem = new JMenuItem("Napravi rezervaciju");
			JMenuItem pregled_rezItem = new JMenuItem("Pregled odobrenih");
			JMenuItem pregled_svih_rezItem = new JMenuItem("Pregled svih rezervacija");

			rezervacijeMenu.add(rezervisiItem);
			rezervacijeMenu.add(pregled_rezItem);
			rezervacijeMenu.add(pregled_svih_rezItem);
			mainMenuRecepcioner.add(rezervacijeMenu);
			
			frame.setJMenuBar(mainMenuRecepcioner);
			
			final Gosti gost = new Gosti();
			//btn_dodaj_rez = new JButton("Napravi rezervaciju");
			rezervisiItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						gost.ZahtevRezervacija(username);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		           }
		    	});
			//panel.add(btn_dodaj_rez);

			//btn_pregled_rez = new JButton("Pregled rezervacija");
			pregled_rezItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						gost.PregledRezervacija(username);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		           }
		    	});
			//panel.add(btn_pregled_rez);
			pregled_svih_rezItem.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {		        	   
		        	   try {
						gost.Pregled_Svih_Rezervacija(username);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		           }
		    	});
			JLabel gost_info = new JLabel("Username: " + username + "       Pozicija: gost");
			panel.add(gost_info);
					
			panel.add(odjava, BorderLayout.SOUTH);
		}
		
		odjava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int opt = JOptionPane.showConfirmDialog(null, "Do you want to log out?", "close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	    		if (opt == JOptionPane.YES_OPTION) {
	    			frame.dispose();
	    			Frame_Setup frameSetup = new Frame_Setup();
	    		}
            }
        });
		
		frame.setVisible(true);
	}
}
