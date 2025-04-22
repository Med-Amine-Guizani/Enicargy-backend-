

delete from reclamation ;

INSERT INTO  user (email, password, role, user_name) values
                                                         ("amine@gmail.com","amine","Admin","amine");

INSERT INTO reclamation (titre, description, local, salle,User_id) VALUES
                                                                                ('Projecteur non fonctionnel', 'Le projecteur de la salle S12 ne s’allume plus malgré plusieurs tentatives.', 'Principal', 'S12',  1),
                                                                                ('Image floue sur le projecteur', 'Le projecteur de la salle S27 affiche une image floue, rendant le cours difficile à suivre.', 'Annex', 'S27',  1),
                                                                                ('Connexion HDMI impossible', 'Impossible de connecter un ordinateur au projecteur de la salle S5 via HDMI.', 'Principal', 'S5', 1);

