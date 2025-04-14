

delete from reclamation ;

INSERT INTO reclamation (titre, description, local, salle, status, userid) VALUES
                                                                                ('Projecteur non fonctionnel', 'Le projecteur de la salle S12 ne s’allume plus malgré plusieurs tentatives.', 'Principal', 'S12', 'En-cours', 1),
                                                                                ('Image floue sur le projecteur', 'Le projecteur de la salle S27 affiche une image floue, rendant le cours difficile à suivre.', 'Annex', 'S27', 'En-cours', 2),
                                                                                ('Connexion HDMI impossible', 'Impossible de connecter un ordinateur au projecteur de la salle S5 via HDMI.', 'Principal', 'S5', 'En-cours', 3);
