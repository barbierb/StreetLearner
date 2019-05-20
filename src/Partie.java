import extensions.Sound;
class Partie {
    boolean isRunning;
    int difficulte;
    int tour;
    int tourMaxi;

    Joueur[] joueurs;
    boolean isPlayerTurn;

    int id_TypeDeQuestion; // 1=quest 2=VF 3=TAT
    int id_Matiere; // 1=fr, 2=Math, 3=hist, 4=angl

    Question[] Questions;
    VraiFaux[] VraiFaux;
    TexteATrou[] TextesATrou;


    int id_questions; // utilisée pour savoir a quelle question on se trouve, -1 pour aucune, 0 à length(Questions).
    int id_vraifaux;
    int id_texteatrou;

    Menu menu;
    int id_curseur; // utilisée dans de multiples contextes
    int LARGEUR; // WIDTH
    int HAUTEUR; // HEIGHT
    long horloge = 0;
    Sound ThemePrincipal;
}

/*

assertion sur x tirage et verif si aucune question rstant ni 2x la meme

    1 fr:     tàt, ok
            question, ok
            vf ok

    2 math:
            question


    3 histoire:   tàt,
                question, ok
                vf

    4 anglais:    tàt,
                question


     COMBO DE PLUSIEURS BONNES REPS A LA SUITE
     EN REGISTREMENT SCORES JJOUEURS
     GESTION DES DEGATS DU MODE TEXTE A TROU


 */