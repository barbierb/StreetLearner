class TexteATrou {
    String texteOriginal;
    String texteAffichable; // Le texte complet à afficher, a rien a changer dedans.
                  // "[...]" en guise de remplacement pour les mots a trouve
    String[] Propositions;
    boolean TexteDejaTire = false;
    int[] tableauIndexAleatoire; // numéros des espaces vides

    int id_MotCourant;
    int nombreDeFautes;
    int maximumPropositions; // length prop * 2
}

/*
     J'aime bien les [...] et boire de la [...]

        (1) Bierre       (2) Pates
*/