

delete from reclamation ;

INSERT INTO  user (email, password, role, user_name) values
                                                         ("amine@gmail.com","$2a$10$R3Ok4nzy76NOFAlsUMa0w.Sxa09f.Zhcg48lEGCKQoYWZtRnMmlMW","ADMIN","amine");

INSERT INTO reclamation (titre, description, local, salle,User_id) VALUES
                                                                                ('Projecteur non fonctionnel', 'Le projecteur de la salle S12 ne s’allume plus malgré plusieurs tentatives.', 'Principal', 'S12',  1),
                                                                                ('Image floue sur le projecteur', 'Le projecteur de la salle S27 affiche une image floue, rendant le cours difficile à suivre.', 'Annex', 'S27',  1),
                                                                                ('Connexion HDMI impossible', 'Impossible de connecter un ordinateur au projecteur de la salle S5 via HDMI.', 'Principal', 'S5', 1);

<<<<<<< HEAD


INSERT INTO article (title, body)
VALUES (
           'L''Énergie Durable à l''ENICarthage : Formation et Innovation pour la Transition Énergétique',
           'L''École Nationale d''Ingénieurs de Carthage (ENICarthage) se positionne comme un acteur clé dans la formation des ingénieurs capables de répondre aux enjeux énergétiques de la Tunisie et du continent africain.

         ### Un pôle d''excellence énergétique
         Avec ses laboratoires spécialisés et ses partenariats industriels, l''ENICarthage développe :
         - Des formations pointues en énergies renouvelables
         - Des projets de recherche sur l''efficacité énergétique
         - Des innovations dans le domaine du smart grid

         ### Les atouts de l''école :
         1. **Pédagogie innovante** : Apprentissage par projets concrets (centrales solaires, éoliennes miniatures...)
         2. **Plateformes technologiques** : Laboratoires équipés de derniers cris en photovoltaïque et stockage d''énergie
         3. **Partenariats stratégiques** : Collaborations avec des entreprises du secteur énergétique tunisien

         ### Les défis actuels :
         - Adapter constamment les programmes aux évolutions technologiques
         - Renforcer les liens université-entreprise
         - Développer davantage de projets étudiants innovants

         **Success Story** : Le projet SolarENI, développé par des étudiants, a remporté le prix de l''innovation énergétique en 2023.

         **Conclusion** :
         L''ENICarthage s''affirme comme un incubateur de talents pour la transition énergétique, formant des ingénieurs capables de concevoir les solutions énergétiques de demain.'
       );
=======
INSERT INTO equipment
VALUES
(50, 40, 5, 3, 1500,1, 'Table'),
(100, 80, 10, 5, 1600,2, 'Chaise'),
(3, 25, 3, 2, 30,3 ,'Projecteur'),
(10, 15, 2, 2, 25,4, 'Télévision '),
(150, 130, 50, 10, 500,5, 'CâbleHDMI'),
(10, 80, 15, 10, 150, 6,'PostedeTravail');
>>>>>>> b3fe76830d36bcdfa167b7ef712a9cd9a7a331dc
