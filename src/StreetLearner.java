import extensions.CSVFile;

class StreetLearner extends Program {

    void testCreerJoueur() {
        Joueur j = creerJoueur("testeur", 20, 5);
        assertEquals("testeur", j.nom);
        assertEquals(20, j.pvMax);
        assertEquals(5, j.pointsDeDegat);
    }

    void testCreerTexteATrou() {
        newSound("");
        String phrase = "Je ne {suis} pas fort en Mathématiques, mais j’ai {été} fort plus {jeune}.";
        TexteATrou tat = creerTexteATrou(phrase);

        assertEquals(tat.texteOriginal, phrase);
        assertEquals(tat.texteAffichable, "Je ne {...} pas fort en Mathématiques, mais j’ai {...} fort plus {...}.");

        assertEquals(tat.Propositions[0], "suis");
        assertEquals(tat.Propositions[1], "été");
        assertEquals(tat.Propositions[2], "jeune");

        assertEquals(tat.maximumPropositions, length(tat.Propositions) * 2);

        assertEquals(nombreDeMots(tat.texteOriginal), 3);
    }

    void testPseudo() {
        assertTrue(pseudoEstCorrect("Benoit", true));
        assertTrue(pseudoEstCorrect("bob", true));

        assertFalse(pseudoEstCorrect("a", true));
        assertFalse(pseudoEstCorrect("", true));
        assertFalse(pseudoEstCorrect("CantinCantinCantin", true));
    }

    Partie p;
    Arts arts;

    void algorithm() {

        reset();
        hide();

        arts = new Arts();
        p = creerNouvellePartie();

        showInterface();
        enableKeyTypedInConsole(true);
        play(p.ThemePrincipal);

        while(p.isRunning) {
            p.horloge++;
            delay(1000);
        }
    }

    void showInterface() {
        clearScreen();
        reset();

        if(p.menu == Menu.PRINCIPAL) {
            printArt(modifierCouleurs(arts.RYU, p.joueurs[0]), (int)(p.HAUTEUR/3.5), (int)(p.LARGEUR/5.5));
            printArt(arts.STREETLEARNER, 3, p.LARGEUR/2 - length(arts.STREETLEARNER[0])/2);

            int posX = p.LARGEUR/2;
            int posY = p.HAUTEUR/2;

            String selection1 = "[1] - "+ANSI_BOLD+"JOUER";
            String selection2 = "[2] - "+ANSI_BOLD+"PERSONNALISATION";
            String selection3 = "[3] - "+ANSI_BOLD+"REGLER LA RESOLUTION";
            String selection4 = "[4] - "+ANSI_BOLD+"QUITTER";

            if(p.id_curseur == 1) {
                printArt(arts.SELECTEUR, posY - 2, posX - length(arts.SELECTEUR[0]) - 2);
                selection1 = "["+ANSI_BOLD+ANSI_UNDERLINE+"1"+ANSI_RESET+"] - "+ANSI_BOLD+"JOUER";

            } else if(p.id_curseur == 2) {
                printArt(arts.SELECTEUR, posY, posX - length(arts.SELECTEUR[0]) - 2);
                selection2 = "["+ANSI_BOLD+ANSI_UNDERLINE+"2"+ANSI_RESET+"] - "+ANSI_BOLD+"PERSONNALISATION";

            } else if(p.id_curseur == 3) {
                printArt(arts.SELECTEUR, posY + 2, posX - length(arts.SELECTEUR[0]) - 2);
                selection3 = "["+ANSI_BOLD+ANSI_UNDERLINE+"3"+ANSI_RESET+"] - "+ANSI_BOLD+"REGLER LA RESOLUTION";

            } else if(p.id_curseur == 4) {
                printArt(arts.SELECTEUR, posY + 4, posX - length(arts.SELECTEUR[0]) - 2);
                selection4 = "["+ANSI_BOLD+ANSI_UNDERLINE+"4"+ANSI_RESET+"] - "+ANSI_BOLD+"QUITTER";

            }

            printAtPos(selection1, posY, posX);
            printAtPos(selection2, posY+2, posX);
            printAtPos(selection3, posY+4, posX);
            printAtPos(selection4, posY+6, posX);

        } else if(p.menu == Menu.CREATION) {
            show();
            enableKeyTypedInConsole(false);
            String nom;

            do {
                drawChar(p.HAUTEUR/2 - 6, 0, 1, p.LARGEUR, "_");
                drawChar(p.HAUTEUR/2 + 5, 0, 1, p.LARGEUR, "_");
                String difficulte = "Difficulté: "+ANSI_GREEN+"FACILE"+ANSI_RESET;
                printAtPos(difficulte, p.HAUTEUR/2 - 2, p.LARGEUR/2- length(difficulte)/2);
                printAtPos("Entrez votre nom de joueur: ", p.HAUTEUR/2, p.LARGEUR/2 - 16);
                nom = readString();

            } while(!pseudoEstCorrect(nom));

            p.joueurs[0].nom = nom;
            p.joueurs[1] = creerJoueur("Ennemi", 20, 2);
            p.menu = Menu.MATIERE;

            hide();
            enableKeyTypedInConsole(true);

            showInterface();
        } else if(p.menu == Menu.MATIERE) {
            int posX = p.LARGEUR/2;
            int posY = p.HAUTEUR/2;

            String selection1 = ANSI_RED+"██   Français";
            String selection2 = ANSI_YELLOW+"██   Mathématiques";
            String selection3 = ANSI_GREEN+"██   Histoire";
            String selection4 = ANSI_BLUE+"██   Anglais";

            if(p.id_Matiere == 1) {
                printArt(arts.SELECTEUR, posY - 11, (int) (posX / 1.5));

            } else if(p.id_Matiere == 2) {
                printArt(arts.SELECTEUR, posY - 5, (int) (posX / 1.5));

            } else if(p.id_Matiere == 3) {
                printArt(arts.SELECTEUR, posY + 1, (int) (posX / 1.5));

            } else if(p.id_Matiere == 4) {
                printArt(arts.SELECTEUR, posY + 7, (int) (posX / 1.5));
            }

            printAtPos(selection1, posY - 9, posX-10);
            printAtPos(selection2, posY - 3, posX-10);
            printAtPos(selection3, posY + 3, posX-10);
            printAtPos(selection4, posY + 9, posX-10);

/*        } else if(p.menu == Menu.CHOIX_EXERCICE) {
            int posX = p.LARGEUR/2;
            int posY = p.HAUTEUR/2;

            String TexteATrou = "";
            String Question = "";
            String VraiFaux = "";

            if(p.id_Matiere == 1) { // fr: tàt, question, vf
                TexteATrou = ANSI_RED+"Texte à Trou "+ANSI_WHITE+"-"+ANSI_YELLOW+" Remplis des cases par des solutions qui te sont proposées.";
                Question = ANSI_RED+"Question "+ANSI_WHITE+"-"+ANSI_YELLOW+" Réponds correctement à des Questions en choisissant la bonne réponse.";
                VraiFaux = ANSI_RED+"Vrai/Faux "+ANSI_WHITE+"-"+ANSI_YELLOW+" Trouves si les affirmations proposées sont vraies ou fausses.";
            } else if(p.id_Matiere == 2) { // math: question
                Question = ANSI_RED+"Question "+ANSI_WHITE+"-"+ANSI_YELLOW+" Réponds correctement à des Questions en choisissant la bonne réponse.";

            } else if(p.id_Matiere == 3) { // histoire: tàt, question, vf
                TexteATrou = ANSI_RED+"Texte à Trou "+ANSI_WHITE+"-"+ANSI_YELLOW+" Remplis des cases par des solutions qui te sont proposées.";
                Question = ANSI_RED+"Question "+ANSI_WHITE+"-"+ANSI_YELLOW+" Réponds correctement à des Questions en choisissant la bonne réponse.";
                VraiFaux = ANSI_RED+"Vrai/Faux "+ANSI_WHITE+"-"+ANSI_YELLOW+" Trouves si les affirmations proposées sont vraies ou fausses.";
            } else if(p.id_Matiere == 4) { // anglais: tàt, question
                TexteATrou = ANSI_RED+"Texte à Trou "+ANSI_WHITE+"-"+ANSI_YELLOW+" Remplis des cases par des solutions qui te sont proposées.";
                Question = "Question - Réponds correctement à des Questions en choisissant la bonne réponse.";
            }

            if(p.id_curseur == 1) {
                printArt(arts.SELECTEUR, posY - 11, (int) (posX / 2.5));

            } else if(p.id_curseur == 2) {
                printArt(arts.SELECTEUR, posY - 5, (int) (posX / 2.5));

            } else if(p.id_curseur == 3) {
                printArt(arts.SELECTEUR, posY + 1, (int) (posX / 2.5));
            }

            printAtPos(TexteATrou, posY - 9, posX - length(TexteATrou) / 2);
            printAtPos(Question, posY - 3, posX - length(Question) / 2);
            printAtPos(VraiFaux, posY + 3, posX - length(VraiFaux) / 2);
*/
        } else if(p.menu == Menu.FIGHT) {
            if(p.isRunning) {
                reset();
                clearScreen();

                showLines();
                showRound();
                showPlayernames();
                showPlayerHealth();
                showPlayerArt();

                //printAtPos(ANSI_RED_BG + "tour" + ANSI_BG_DEFAULT_COLOR, 1, 1);
                if (p.isPlayerTurn) {
                    //printAtPos(ANSI_RED_BG + "player turn" + ANSI_BG_DEFAULT_COLOR, 2, 1);

                    if (p.id_TypeDeQuestion == 1) {
                        //printAtPos(ANSI_RED_BG + "question" + ANSI_BG_DEFAULT_COLOR, 3, 1);
                        if (p.id_questions == -1) {
                            p.id_questions = getQuestionIdAleatoire();
                        }

                        Question q = p.Questions[p.id_questions];
                        int posX = p.LARGEUR / 5;
                        int posY = length(arts.ENNEMI) + 18;

                        for (int idx = 1; idx < 5; idx++) {
                            String proposition = "[" + idx + "] " + q.propositions[idx - 1];
                            printAtPos(proposition, posY, posX * idx - length(proposition) / 2);
                        }

                        printAtPos(q.enonce, length(arts.ENNEMI) + 14, p.LARGEUR / 2 - length(q.enonce) / 2);

                        if (p.id_curseur != 0) {
                            boolean AttaqueReussie = (p.id_curseur == q.id_bonneReponse);
                            makeDamage(AttaqueReussie, p.joueurs[0], p.joueurs[1]);
                            showQuestionResult(AttaqueReussie);
                            p.id_questions = -1;
                            q.dejaPosee = true;
                        }

                    } else if (p.id_TypeDeQuestion == 2) {
                        //printAtPos(ANSI_RED_BG + "vraifaux" + ANSI_BG_DEFAULT_COLOR, 3, 1);
                        int posY = length(arts.ENNEMI) + 15;

                        if (p.id_vraifaux == -1) {
                            p.id_vraifaux = getVraiFauxIdAleatoire();
                        }
                        VraiFaux vf = p.VraiFaux[p.id_vraifaux];
                        //printAtPos(ANSI_RED_BG + "id: " + p.id_vraifaux + " bonnerep="+ vf.reponse + ANSI_BG_DEFAULT_COLOR, 4, 1);
                        printAtPos(vf.enonce, posY, p.LARGEUR / 2 - length(vf.enonce) / 2);

                        String vrai = "[1] Vrai", faux = "[2] Faux";
                        printAtPos(vrai, posY + 5, p.LARGEUR / 3 - length(vrai) / 2);
                        printAtPos(faux, posY + 5, p.LARGEUR / 3 * 2 - length(faux) / 2);

                        if (p.id_curseur != 0) {
                            boolean AttaqueReussie = p.id_curseur == vf.reponse;
                            //printAtPos(ANSI_RED_BG + "atk reussi: "+AttaqueReussie+ ANSI_BG_DEFAULT_COLOR, 6, 1);
                            makeDamage(AttaqueReussie, p.joueurs[0], p.joueurs[1]);
                            showVraiFauxResult(AttaqueReussie);
                            p.id_vraifaux = -1;
                            vf.dejaPosee = true;
                        }

                    } else if (p.id_TypeDeQuestion == 3) {
                        if (p.id_texteatrou == -1) {
                            p.id_texteatrou = getTexteATrouIdAleatoire();
                        }

                        TexteATrou tat = p.TextesATrou[p.id_texteatrou];
                        //printAtPos(ANSI_RED_BG + "texteatrou " + length(tat.texteAffichable) + ANSI_BG_DEFAULT_COLOR, 3, 1);
                        int posY = length(arts.ENNEMI) + 15;
                        int taille = length(tat.Propositions);
                        int posX = p.LARGEUR / (taille + 1);
                        printAtPos(tat.texteAffichable, length(arts.ENNEMI) + 14, p.LARGEUR / 2 - length(tat.texteAffichable) / 2);
                        printAtPos("Essais restants: " + (tat.maximumPropositions - tat.nombreDeFautes) + " / " + tat.maximumPropositions, posY - 3, 6);

                        for (int i = 1; i <= taille; i++) {
                            for (int j = 0; j < taille; j++) {
                                if (tat.tableauIndexAleatoire[j] == i) {
                                    String texte = "[" + i + "] " + tat.Propositions[j];
                                    printAtPos(texte, length(arts.ENNEMI) + 20, posX * i - length(texte) / 2);
                                }
                            }
                        }
                    }

                    if (p.id_questions == -1 && p.id_vraifaux == -1 && p.id_texteatrou == -1) { //TODO a modif c pas ouf
                        p.id_curseur = 0;
                        p.isPlayerTurn = false;
                    }

                } else {
                    reset();
                    //printAtPos(ANSI_RED_BG + "ennemi turn" + ANSI_BG_DEFAULT_COLOR, 2, 1);
                    if (attaqueAleatoire(p.difficulte)) {
                        makeDamage(true, p.joueurs[1], p.joueurs[0]);
                    } else {
                        makeDamage(false, p.joueurs[1], p.joueurs[0]);
                    }
                    p.isPlayerTurn = true;
                    p.tour++;
                }

                verifierTour();
            }


        } else if(p.menu == Menu.PERSONNALISATION) {
            printArt(modifierCouleurs(arts.RYU, p.joueurs[0]), (int)(p.HAUTEUR/3.5), (int)(p.LARGEUR/5.5));

            int posX = p.LARGEUR/2 + 5;
            int posY = p.HAUTEUR/2;

            if(p.id_curseur == 1) {
                printArt(arts.SELECTEUR, posY - 11, posX - length(arts.SELECTEUR[0]) - 3);
            } else if(p.id_curseur == 2) {
                printArt(arts.SELECTEUR, posY - 8, posX - length(arts.SELECTEUR[0]) - 3);
            } else if(p.id_curseur == 3) {
                printArt(arts.SELECTEUR, posY - 5, posX - length(arts.SELECTEUR[0]) - 3);
            } else if(p.id_curseur == 4) {
                printArt(arts.SELECTEUR, posY - 2, posX - length(arts.SELECTEUR[0]) - 3);
            } else if(p.id_curseur == 5) {
                printArt(arts.SELECTEUR, posY + 1, posX - length(arts.SELECTEUR[0]) - 3);
            } else if(p.id_curseur == 6) {
                printArt(arts.SELECTEUR, posY + 4, posX - length(arts.SELECTEUR[0]) - 3);
            }
            Joueur j = p.joueurs[0];
            printAtPos(j.COULEURS[j.couleurs[0]]+"██", posY-9, posX);
            printAtPos(j.COULEURS[j.couleurs[1]]+"██", posY-6, posX);
            printAtPos(j.COULEURS[j.couleurs[2]]+"██", posY-3, posX);
            printAtPos(j.COULEURS[j.couleurs[3]]+"██", posY, posX);
            printAtPos(j.COULEURS[j.couleurs[4]]+"██", posY+3, posX);
            printAtPos(j.COULEURS[j.couleurs[5]]+"██", posY+6, posX);

        } else if(p.menu == Menu.RESOLUTION) {
            showContours();
            String message = ANSI_YELLOW+"Utilisez les flèches directionnelles";
            printAtPos(message, p.HAUTEUR/2, p.LARGEUR / 2 - length(message) / 2);

        } else if(p.menu == Menu.WIN) {
            for(int i=0; i<100; i++) {
                printArt(modifierCouleurs(arts.RYU, p.joueurs[0]), (int)(random()*p.HAUTEUR), (int)(random()*p.LARGEUR));
            }
            String message = "VOUS AVEZ GAGNE.";
            text(randomANSIColor());
            printAtPos(message, p.HAUTEUR/2, p.LARGEUR / 2 - length(message) / 2);
        } else if(p.menu == Menu.LOSE) {
            for(int i=0; i<100; i++) {
                printArt(modifierCouleurs(arts.ENNEMI, p.joueurs[1]), (int)(random()*p.HAUTEUR), (int)(random()*p.LARGEUR));
            }
            String message = "VOUS AVEZ PERDU.";
            text(randomANSIColor());
            printAtPos(message, p.HAUTEUR/2, p.LARGEUR / 2 - length(message) / 2);
        }
    }

    void showVraiFauxResult(boolean AttaqueReussie) {
        int posY = length(arts.ENNEMI) + 15;
        VraiFaux vf = p.VraiFaux[p.id_vraifaux];
        String prefV = "[1] Vrai", prefF = "[2] Faux";

        if(!AttaqueReussie && vf.reponse == 1) {
            prefV = ANSI_GREEN;
            prefF = ANSI_RED;
        } else {
            prefF = ANSI_GREEN;
            prefV = ANSI_RED;
        }

        printAtPos(prefV+"[1] Vrai", posY+5, p.LARGEUR/3 - length("[1] Vrai") / 2);
        printAtPos(prefF+"[2] Faux", posY+5, p.LARGEUR/3*2 - length("[2] Faux") / 2);
    }

    void makeDamage(boolean AttaqueReussie, Joueur attaquant, Joueur victime) {
        if(AttaqueReussie) {
            String message = ANSI_YELLOW + attaquant.nom + " a réussi son attaque! [-" + attaquant.pointsDeDegat + "PV]";
            printAtPos(message, p.HAUTEUR / 2 + 20, p.LARGEUR / 2 - (length(message) / 2));
            victime.pv -= attaquant.pointsDeDegat;
            showPlayerHealth();
        } else {
            String message = ANSI_RED + attaquant.nom + " a raté son attaque";
            printAtPos(message, p.HAUTEUR / 2 + 20, p.LARGEUR / 2 - (length(message) / 2));
        }
    }

    void showQuestionResult(boolean AttaqueReussie) {
        int posX = p.LARGEUR / 5;
        int posY = length(arts.ENNEMI) + 18;
        Question q = p.Questions[p.id_questions];
        if(!AttaqueReussie) {
            String proposition = "[" + p.id_curseur + "] " + q.propositions[p.id_curseur - 1];
            printAtPos(ANSI_RED + proposition, posY, posX * p.id_curseur - length(proposition) / 2);
        }
        String proposition = "[" + q.id_bonneReponse + "] " + q.propositions[q.id_bonneReponse - 1];
        printAtPos(ANSI_GREEN +proposition, posY, posX * q.id_bonneReponse - length(proposition) / 2);
    }

    void verifierTour() {
        boolean pdvOK = true;
        String message = "";
        if(p.tour > p.tourMaxi) {
            message = ANSI_RED+"Tour maximum atteint";
            p.menu = Menu.WIN;
            pdvOK = false;

        } else if(p.joueurs[0].pv < 1) {
            message = ANSI_RED+"Vous avez perdu tous vos points de vie!";
            p.menu = Menu.LOSE;
            pdvOK = false;

        } else if(p.joueurs[1].pv < 1) {
            message = ANSI_GREEN+"Vous avez gagné la partie!";
            p.menu = Menu.WIN;
            pdvOK = false;
        } else if(p.id_TypeDeQuestion == 1 && p.tour > length(p.Questions)) {
            p.id_TypeDeQuestion++;
            //printAtPos(ANSI_RED_BG+"passage aux vrai faux"+ANSI_BG_DEFAULT_COLOR,7,1);

        } else if(p.id_TypeDeQuestion == 2 && p.tour > length(p.Questions) + length(p.VraiFaux)) {
            p.id_TypeDeQuestion++;
            //printAtPos(ANSI_RED_BG+"passage aux textes a trou"+ANSI_BG_DEFAULT_COLOR,7,1);

        } else if(p.id_TypeDeQuestion == 3 && p.TextesATrou[p.id_texteatrou].id_MotCourant > p.TextesATrou[p.id_texteatrou].maximumPropositions/2) {
            //printAtPos(ANSI_RED_BG+"tour fini"+ANSI_BG_DEFAULT_COLOR,7,1);
        }

        if(!pdvOK) {
            drawChar(p.HAUTEUR/2+15, 0, 5, p.LARGEUR, " ");
            printAtPos(message+ANSI_RESET, p.HAUTEUR/2+15, p.LARGEUR/2-(length(message) / 2));
        }
    }

    void keyTypedInConsole(char c) {
        int code = (int) c;
        if(p.menu == Menu.PRINCIPAL) {
            if (code == 13) {
                printAtPos(p.id_curseur + "", 6, 6);
                if (p.id_curseur == 1) {
                    p.menu = Menu.CREATION;
                } else if (p.id_curseur == 2) {
                    p.menu = Menu.PERSONNALISATION;
                } else if (p.id_curseur == 3) {
                    p.menu = Menu.RESOLUTION;
                } else {
                    killProg();
                }
                p.id_curseur = 1;

            } else if (c == '1') {
                p.menu = Menu.CREATION;

            } else if (c == '2') {
                p.menu = Menu.RESOLUTION;

            } else if (c == '3') {
                p.menu = Menu.PERSONNALISATION;

            } else if (c == '4') {
                killProg();

            } else if (c == ANSI_DOWN) {
                if (p.id_curseur < 4) {
                    p.id_curseur++;
                }
            } else if (c == ANSI_UP) {
                if (p.id_curseur > 1) {
                    p.id_curseur--;
                }
            }
            showInterface();
        } else if(p.menu == Menu.PERSONNALISATION) {
            if (code == 13 || code == 127) {
                p.menu = Menu.PRINCIPAL;
                p.id_curseur = 1;

            } else if (c == ANSI_DOWN) {
                if (p.id_curseur < 6) {
                    p.id_curseur++;
                }
            } else if (c == ANSI_UP) {
                if (p.id_curseur > 1) {
                    p.id_curseur--;
                }
            } else if (c == ANSI_LEFT) {
                if(p.joueurs[0].couleurs[p.id_curseur-1] > 0) {
                    p.joueurs[0].couleurs[p.id_curseur-1] -= 1;
                }

            } else if (c == ANSI_RIGHT) {
                if(p.joueurs[0].couleurs[p.id_curseur-1] < 7) {
                    p.joueurs[0].couleurs[p.id_curseur-1] += 1;
                }

            }
            showInterface();
        } else if(p.menu == Menu.MATIERE) {
            if (code == 13) {
                p.menu = Menu.AUCUN;
                stop(p.ThemePrincipal);
                animReadyFight();
                chargerPartie();
                p.tourMaxi = length(p.TextesATrou) + length(p.Questions) + length(p.VraiFaux);
                p.menu = Menu.FIGHT;

            } else if (c == ANSI_DOWN) {
                if (p.id_Matiere < 4) {
                    p.id_Matiere=p.id_Matiere;
                }
            } else if (c == ANSI_UP) {
                if (p.id_Matiere > 1) {
                    p.id_Matiere--;
                }
            }
            showInterface();

 /*       } else if(p.menu == Menu.CHOIX_EXERCICE) {
            if (code == 13) {
                p.menu = Menu.FIGHT;
                chargerPartie();
                if(p.id_TypeDeQuestion == 1) {
                    p.tourMaxi = length(p.Questions);
                } else if(p.id_TypeDeQuestion == 2) {
                    p.tourMaxi = length(p.VraiFaux);
                } else if(p.id_TypeDeQuestion == 3) {
                    p.tourMaxi = length(p.TextesATrou);
                }
                animReadyFight();
                showInterface();
                return;
            } else if (code == 127) {
                p.menu = Menu.MATIERE;
                showInterface();
                return;
            }
            int max = 3; // en fr ou histoire
            int min = 1; // tt matieres sauf math
            if(p.id_Matiere == 2 || p.id_Matiere == 4) { // en math ou en anglais
                max = 2;
            }
            if(p.id_Matiere == 2) { // en math seulement
                min = 2;
            }
            if (c == ANSI_DOWN) {
                if (p.id_curseur < max) {
                    p.id_curseur++;
                }
            } else if (c == ANSI_UP) {
                if (p.id_curseur > min) {
                    p.id_curseur--;
                }
            }
            showInterface();*/
        } else if(p.menu == Menu.RESOLUTION) {
            if (c == ANSI_UP && p.HAUTEUR > 50) {
                p.HAUTEUR--;
            } else if(c==ANSI_DOWN && p.HAUTEUR < 60) {
                p.HAUTEUR++;
            } else if(c==ANSI_RIGHT && p.LARGEUR < 240) {
                p.LARGEUR++;
            } else if(c==ANSI_LEFT && p.LARGEUR > 200) {
                p.LARGEUR--;
            } else if(code==13) {
                p.menu = Menu.PRINCIPAL;
            }
            showInterface();

        } else if(p.menu == Menu.FIGHT) {
            if(p.id_TypeDeQuestion == 1) {
                if (p.id_questions != -1) {
                    if (c == '1' || c == '&') {
                        p.id_curseur = 1;
                    } else if (c == '2' || c == 'é') {
                        p.id_curseur = 2;
                    } else if (c == '3' || c == '"') {
                        p.id_curseur = 3;
                    } else if (c == '4' || c == '\'') {
                        p.id_curseur = 4;
                    }
                }
            } else if(p.id_TypeDeQuestion == 2) {
                if (p.id_vraifaux != -1) {
                    if (c == '1' || c == '&' || c == 'v') {
                        p.id_curseur = 1;
                    } else if (c == '2' || c == 'é' || c == 'f') {
                        p.id_curseur = 2;
                    }
                }
            } else if(p.id_TypeDeQuestion == 3) {
                if (p.id_texteatrou != -1) {
                    TexteATrou tat = p.TextesATrou[p.id_texteatrou];
                    if(c>'0' && c<(char)('1' + length(tat.Propositions))) {
                        int numero = charToInt(c);
                        if(tat.nombreDeFautes >= tat.maximumPropositions) {
                            p.id_texteatrou = -1;
                            drawChar(length(arts.ENNEMI) + 14, 0, 1, p.LARGEUR, " ");
                            drawChar(length(arts.ENNEMI) + 20, 0, 1, p.LARGEUR, " ");
                            printAtPos(tat.texteOriginal, length(arts.ENNEMI) + 14, p.LARGEUR / 2 - length(tat.texteAffichable) / 2);
                            makeDamage(false, p.joueurs[0], p.joueurs[1]);
                            return;
                        }
                        for(int j=0; j<length(tat.Propositions); j++) {
                            if(tat.tableauIndexAleatoire[j] == numero) {
                                if(j == tat.id_MotCourant) {
                                    placerMotDansTexteATrouCourant(tat.Propositions[tat.id_MotCourant]);
                                    tat.Propositions[tat.id_MotCourant] = ANSI_GREEN+tat.Propositions[tat.id_MotCourant]+ANSI_TEXT_DEFAULT_COLOR;
                                    tat.id_MotCourant++;
                                } else {
                                    tat.nombreDeFautes++;
                                }
                            }
                        }
                        if(tat.id_MotCourant >= tat.maximumPropositions/2) {
                            showInterface();
                            p.id_texteatrou = -1;
                            tat.TexteDejaTire = true;
                            drawChar(length(arts.ENNEMI) + 20, 0, 1, p.LARGEUR, " ");
                            makeDamage(true, p.joueurs[0], p.joueurs[1]);
                            return;
                        }
                    }
                }

            }
            showInterface();
        } else if(p.menu == Menu.WIN || p.menu == Menu.LOSE) {
            if (code == 127) {
                killProg();
            } else {
                showInterface();
            }
        }

        if(c == 'k') {
            killProg();
        }
    }

    void placerMotDansTexteATrouCourant(String mot) {
        String NouvellePhrase = "";
        String TexteOriginel = p.TextesATrou[p.id_texteatrou].texteAffichable;
        boolean dejaFait = false;
        //printAtPos(ANSI_RED_BG+"PLACERMOT"+ANSI_BG_DEFAULT_COLOR,10,1);
        for(int i=0; i<length(TexteOriginel); i++) {
            char c = charAt(TexteOriginel, i);
            //printAtPos(ANSI_RED_BG+"i= "+i+" c= "+c+ANSI_BG_DEFAULT_COLOR,11,1);
            if(c == '{' && !dejaFait) {
                //printAtPos(ANSI_RED_BG+"if1"+c+ANSI_BG_DEFAULT_COLOR,12,1);
                while(charAt(TexteOriginel, i) != '}') {
                    //printAtPos(ANSI_RED_BG+"i="+i+ANSI_BG_DEFAULT_COLOR,13,1);
                    i++;
                }
                dejaFait = true;
                NouvellePhrase += ANSI_GREEN+mot+ANSI_TEXT_DEFAULT_COLOR;
                ///printAtPos(ANSI_RED_BG+NouvellePhrase+ANSI_BG_DEFAULT_COLOR,15,1);
            } else {
                NouvellePhrase += c;
                //printAtPos(ANSI_RED_BG+NouvellePhrase+ANSI_BG_DEFAULT_COLOR,15,1);
            }
        }
        p.TextesATrou[p.id_texteatrou].texteAffichable = NouvellePhrase;
    }

    int nombreDeMots(String texte){
        char c;
        int compteurMots = 0;
        for(int idx=0; idx < length(texte); idx++){
            c = charAt(texte, idx);
            if(c == '{') {
                compteurMots++;
            }
        }
        return compteurMots;
    }

    TexteATrou creerTexteATrou(String TexteAAnalyser){
        String[] motsAtrouver = new String[nombreDeMots(TexteAAnalyser)];
        boolean dansCrochets = false;
        int cptPlaceDansTableau = 0;
        String occurence = "";
        String texteAvecTrou = "";
        int[]tableauIndexAleatoire = initialiserTableau(nombreDeMots(TexteAAnalyser));


        for(int idx=0; idx < length(TexteAAnalyser); idx++){
            char c = charAt(TexteAAnalyser, idx);
            if(c == '{'){
                dansCrochets = true;
                texteAvecTrou = texteAvecTrou + "{...}";
            }else if(c == '}') {
                dansCrochets = false;
                motsAtrouver[cptPlaceDansTableau] = occurence;
                occurence = "";
                cptPlaceDansTableau ++;
            }else{
                if (dansCrochets){
                    occurence = occurence + c;
                }else{
                    texteAvecTrou = texteAvecTrou + c;
                }
            }
        }

        TexteATrou tat = new TexteATrou();
        tat.texteAffichable = texteAvecTrou;
        tat.Propositions = motsAtrouver;
        tat.tableauIndexAleatoire = tableauIndexAleatoire;
        tat.texteOriginal = TexteAAnalyser;

        tat.id_MotCourant = 0;
        tat.nombreDeFautes = 0;
        tat.maximumPropositions = length(tat.Propositions)*2;
        return tat;
    }

    int[] initialiserTableau(int nombreLignes) {

        int[] TableauIndicePropositions = new int[nombreLignes];
        int tmp;
        int indice1;
        int indice2;

        for(int idx=0; idx<length(TableauIndicePropositions); idx++){
            TableauIndicePropositions[idx] = idx + 1 ;
        }

        for(int idx=0; idx<1000; idx++){
            indice1 = (int) (random() * nombreLignes);
            indice2 = (int) (random() * nombreLignes);

            tmp = TableauIndicePropositions[indice1];
            TableauIndicePropositions[indice1] = TableauIndicePropositions[indice2];
            TableauIndicePropositions[indice2] = tmp;

        }
        return TableauIndicePropositions;
    }

    int getQuestionIdAleatoire() {
        int id = 0;
        while(id == 0) {
            id = (int) (random() * length(p.Questions));
            Question q = p.Questions[id];
            if(q.dejaPosee) {
                id = 0;
            } else {
                return id;
            }
        }
        return id;
    }

    int getVraiFauxIdAleatoire() {
        int id = 0;
        while(id == 0) {
            id = (int) (random() * length(p.VraiFaux));
            VraiFaux vf = p.VraiFaux[id];
            if(vf.dejaPosee) {
                id = 0;
            } else {
                return id;
            }
        }
        return id;
    }

    int getTexteATrouIdAleatoire() {
        int id = 0;
        while(id == 0) {
            id = (int) (random() * length(p.TextesATrou));
            TexteATrou tat = p.TextesATrou[id];
            if(tat.TexteDejaTire) {
                id = 0;
            } else {
                return id;
            }
        }
        return id;
    }

    void printArt(String[] tab, int y, int x) {
        for(int i = 0; i < length(tab, 1); i++) {
            cursor(y+i,x);
            print(tab[i]);
        }
        text("Black");
    }




    String[] modifierCouleurs(String[] pixelArt, Joueur j){
        String aTransformer = "";
        String transforme = "";
        boolean blockNoirTrouve = false;
        char symbole;

        for(int idx=0; idx < length(pixelArt); idx++){
            aTransformer = pixelArt[idx];
            for(int idxChar = length(aTransformer) - 1; idxChar >= 0; idxChar-- ){
                symbole = charAt(aTransformer, idxChar);
                if( symbole == '█'){
                    blockNoirTrouve = true;
                }

                if(!blockNoirTrouve){
                    transforme = ':' + transforme;
                }else{
                    transforme = symbole + transforme;
                }

            }
            pixelArt[idx] = transforme;
            blockNoirTrouve = false;
            transforme = "";
        }


        String couleur = "";
        String artTransforme[] = new String[length(pixelArt)];
        boolean ajoutblock = true;
        char symbolePrecedent = '$'; //initialisation pour le premier tour

        if(j == null) {
            j = creerJoueur("MenuPrincipal", 1, 1);
        }

        for(int idx=0; idx < length(pixelArt); idx++){
            aTransformer = pixelArt[idx];
            for(int idxChar=0; idxChar < length(aTransformer); idxChar++ ){
                symbole = charAt(aTransformer, idxChar);

                if(symbole == symbolePrecedent && symbolePrecedent != ' ' && symbolePrecedent != ':'){
                    transforme = transforme + '█';
                }else{

                    if(symbole == '█'){
                        blockNoirTrouve = true;
                        couleur = j.COULEURS[j.couleurs[0]];

                    } else if (symbole == ' '){
                        if(blockNoirTrouve){
                            couleur =  j.COULEURS[j.couleurs[1]];
                        }else{
                            ajoutblock = false;
                        }
                    }else if (symbole == '▓' ){
                        couleur = j.COULEURS[j.couleurs[2]];

                    }else if (symbole =='░' ){
                        couleur =  j.COULEURS[j.couleurs[3]];

                    }else if (symbole =='@' ) {
                        couleur = j.COULEURS[j.couleurs[4]];
                    }else if (symbole =='▒' ) {
                        couleur =  j.COULEURS[j.couleurs[5]];
                    }else if (symbole ==':' ) {
                        ajoutblock = false;
                    }
                    if(ajoutblock){
                        transforme = transforme + couleur + '█' ;
                    }else{
                        transforme = transforme + ' ';
                    }
                    ajoutblock = true;
                    couleur = "";
                }
                symbolePrecedent = symbole;
            }

            blockNoirTrouve = false;
            artTransforme[idx] = transforme;
            transforme = "";
        }
        //mettre reset je ne sais plus comment cela fonctionne
        return artTransforme;
    }

    void drawChar(int y, int x, int taille_y, int taille_x, String texte) {
        for(int i = 0; i < taille_y; i++) {
            for(int j = 0; j < taille_x; j++) {
                printAtPos(texte, y+i, x+j);
            }
        }
    }

    void printAtPos(String texte, int y, int x) {
        cursor(y,x);
        print(texte);
    }

    void showPlayerHealth() {
        String PV_Joueur = ANSI_GREEN;
        for(int pv = 0; pv<p.joueurs[0].pv; pv++) {
            PV_Joueur += "█";
        }
        int diff1 = (p.joueurs[0].pvMax -p.joueurs[0].pv);
        if(diff1>0) {
            PV_Joueur += ANSI_RED;
            for(int pv=0;pv<diff1; pv++) {
                PV_Joueur += "█";
            }
        }

        String PV_Ennemi = ANSI_GREEN;
        for(int pv = 0; pv<p.joueurs[1].pv; pv++) {
            PV_Ennemi += "█";
        }
        int diff2 = (p.joueurs[1].pvMax -p.joueurs[1].pv);
        if(diff2>0) {
            PV_Ennemi += ANSI_RED;
            for(int pv=0;pv<diff2; pv++) {
                PV_Ennemi += "█";
            }
        }
        PV_Joueur += ANSI_RESET;
        PV_Ennemi += ANSI_RESET;
        printAtPos(PV_Joueur, 6, p.LARGEUR/4 - 10);
        printAtPos(PV_Ennemi, 6, p.LARGEUR/2 + p.LARGEUR/4 - 10);
    }

    void showPlayerArt() {
        printArt(modifierCouleurs(arts.RYU, p.joueurs[0]), 9, p.LARGEUR/4 - length(arts.RYU[1])/2 );
        printArt(modifierCouleurs(arts.ENNEMI, null), 8, p.LARGEUR/2 + p.LARGEUR/4 - length(arts.RYU[1])/2);
    }

    void showRound() {
        String round = "[ ROUND "+p.tour+"/"+p.tourMaxi+" ]";
        printAtPos(round, 2, p.LARGEUR / 2 - length(round) / 2);
    }

    void showLines() {
        drawChar(length(arts.ENNEMI)+9, 0, 1, p.LARGEUR, "_");
        //drawChar(0, 0, length(arts.ENNEMI)+9, p.LARGEUR, ANSI_CYAN_BG+" ");
        //drawChar(length(arts.ENNEMI)+27, 0, 5, p.LARGEUR, ANSI_CYAN_BG+" ");
        //print(ANSI_BG_DEFAULT_COLOR);
        drawChar(p.HAUTEUR-3, 0, 1, p.LARGEUR, "_");
    }

    void showPlayernames() {
        printAtPos(p.joueurs[0].nom, 4, p.LARGEUR/4 - length(p.joueurs[0].nom)/2);
        printAtPos(p.joueurs[1].nom, 4, p.LARGEUR/2 + p.LARGEUR/4 - length(p.joueurs[1].nom)/2);
    }

    Joueur creerJoueur(String nom, int pv, int dmg) {
        Joueur j = new Joueur();
        j.nom = nom;
        j.pointsDeDegat = dmg;
        j.pvMax = pv;
        j.pv = pv;
        j.COULEURS = new String[] {
                ANSI_BLACK,
                ANSI_RED,
                ANSI_GREEN,
                ANSI_YELLOW,
                ANSI_BLUE,
                ANSI_PURPLE,
                ANSI_CYAN,
                ANSI_WHITE
        };
        j.couleurs = new int[] { 0, 4, 7, 3, 7, 1 };
        return j;
    }

    String toString(Joueur j) {
        return "Joueur{nom="+j.nom+",pv="+j.pv +"}";
    }

    void killProg() {
        p.menu = Menu.AUCUN;
        stop(p.ThemePrincipal);
        enableKeyTypedInConsole(false);
        clearScreen();
        reset();
        cursor(0,0);
        p.isRunning = false;
    }

    void showContours() {
        print(ANSI_RED);
        printAtPos("██", 1, 1);
        printAtPos("██", 2, 1);
        printAtPos("██", 3, 1);
        printAtPos("██", 1, 3);
        printAtPos("██", 1, 5);

        printAtPos("██", 1, p.LARGEUR-2);
        printAtPos("██", 1, p.LARGEUR-4);
        printAtPos("██", 1, p.LARGEUR-6);
        printAtPos("██", 2, p.LARGEUR-2);
        printAtPos("██", 3, p.LARGEUR-2);

        printAtPos("██", p.HAUTEUR-1, 1);
        printAtPos("██", p.HAUTEUR-2, 1);
        printAtPos("██", p.HAUTEUR, 1);
        printAtPos("██", p.HAUTEUR, 3);
        printAtPos("██", p.HAUTEUR, 5);

        printAtPos("██", p.HAUTEUR, p.LARGEUR-2);
        printAtPos("██", p.HAUTEUR-1, p.LARGEUR-2);
        printAtPos("██", p.HAUTEUR-2, p.LARGEUR-2);
        printAtPos("██", p.HAUTEUR, p.LARGEUR-4);
        printAtPos("██", p.HAUTEUR, p.LARGEUR-6);

        print(ANSI_WHITE);
    }

    boolean pseudoEstCorrect(String pseudo) {
        return pseudoEstCorrect(pseudo, false);
    }

    boolean pseudoEstCorrect(String pseudo, boolean estAssert) {
        clearScreen();
        boolean estCorrect = true;
        String erreur = "";

        if(length(pseudo) < 3) {
            erreur = "Votre pseudo doit comporter au moins 3 caractères.";
            estCorrect =  false;

        } else if(length(pseudo) > 16) {
            erreur = "Votre pseudo doit comporter moins de 16 caractères.";
            estCorrect =  false;
        }

        if(!estCorrect && !estAssert) {
            printAtPos(ANSI_YELLOW+erreur+ANSI_TEXT_DEFAULT_COLOR,
                    p.HAUTEUR/2+2
                    ,
                    p.LARGEUR/2-(length(erreur) / 2));
        }
        return estCorrect;
    }
    /*
        boolean reponseEstCorrecte(String reponse) {
            boolean estCorrect = true;
            String erreur = "";
            if(equals(reponse, "")) {
                erreur = "Veuillez écrire le numéro de votre choix de réponse.";
                estCorrect =  false;

            } else if(length(reponse) > 1 || length(reponse) < 1) {
                erreur = "Votre réponse ne doit comporter qu'un seul caractère.";
                estCorrect =  false;

            } else if(charAt(reponse, 0) < '1' || charAt(reponse, 0) > '4') {
                erreur = "Votre réponse doit être comprise entre 1 et 4.";
                estCorrect =  false;
            }

            if(!estCorrect) {
                printAtPos(ANSI_YELLOW+erreur+ANSI_TEXT_DEFAULT_COLOR,
                        p.HAUTEUR/2+18,
                        p.LARGEUR/2-(length(erreur) / 2));
            }
            return estCorrect;
        }
    */
    void animReadyFight() {
        clearScreen();
        printAtPos("ROUND 1.", p.HAUTEUR/2, p.LARGEUR/2);
        delay(300);
        clearScreen();
        printAtPos("ROUND 1..", p.HAUTEUR/2, p.LARGEUR/2);
        delay(300);
        clearScreen();
        printAtPos("ROUND 1...", p.HAUTEUR/2, p.LARGEUR/2);
        delay(300);
        clearScreen();
        printAtPos("FIGHT !", p.HAUTEUR/2, p.LARGEUR/2);
        delay(500);
        p.id_curseur = 0;
    }

    void chargerPartie() {
        String txt_questions = "";
        String txt_vf = "";
        String txt_tat = "";

        if(p.id_Matiere == 1) { // FRANCAIS
            txt_questions = "FR_Questions.csv";
            txt_vf = "FR_VraiFaux.csv";
            txt_tat = "FR_TexteATrou.csv";
        } else if(p.id_Matiere ==2) { // Maths
        } else if(p.id_Matiere ==3) { // Histoire
            txt_questions = "HIST_Questions.csv";
        } else if(p.id_Matiere ==4) { // Anglais
        }

        if(txt_questions != "") {
            CSVFile tableur = loadCSV(txt_questions, ',');
            Question[] questions = new Question[rowCount(tableur)];
            for (int i = 0; i < rowCount(tableur); i++) {
                String[] propositions = new String[4];
                for(int j=1; j<=4; j++) {
                    propositions[j-1] = getCell(tableur, i, j);
                }
                int bonneRep = charToInt(charAt(getCell(tableur, i, 5), 0));
                questions[i] = creerQuestion(getCell(tableur, i, 0), propositions, bonneRep);
            }
            p.Questions = questions;
        }

        if(txt_vf != "") {
            CSVFile tableur = loadCSV(txt_vf, ';');
            VraiFaux[] vf = new VraiFaux[rowCount(tableur)];
            for (int i = 0; i < rowCount(tableur); i++) {
                String enonce = getCell(tableur, i, 0);
                int rep = charToInt(charAt(getCell(tableur, i, 1), 0));
                String texteSupp = getCell(tableur, i, 2);
                vf[i] = creerVraiFaux(enonce, rep, texteSupp);
            }
            p.VraiFaux = vf;
        }

        if(txt_tat != "") {
            CSVFile tableur = loadCSV(txt_tat, ';');
            TexteATrou[] tat = new TexteATrou[rowCount(tableur)];
            for (int i = 0; i < rowCount(tableur); i++) {
                tat[i] = creerTexteATrou(getCell(tableur, i, 0));
            }
            p.TextesATrou = tat;
        }

    }

    Partie creerNouvellePartie() {
        Partie p = new Partie();
        p.isRunning = true;
        p.difficulte = 1;
        p.tour = 1;

        p.joueurs = new Joueur[2];
        p.isPlayerTurn = true;
        p.joueurs[0] = creerJoueur("Joueur", 20, 3);

        p.id_Matiere = 1;
        p.id_TypeDeQuestion = 1;

        p.id_questions = -1;
        p.id_vraifaux = -1;
        p.id_texteatrou = -1;

        p.menu = Menu.PRINCIPAL;
        p.id_curseur = 1;
        p.LARGEUR = 212;
        p.HAUTEUR = 55;
        p.ThemePrincipal = newSound("StreetFighterTheme.mp3");

        return p;
    }

    Question creerQuestion(String enonce, String[] propositions, int bonneRep) {
        Question q = new Question();
        q.enonce = enonce;
        q.propositions = propositions;
        q.id_bonneReponse = bonneRep;
        return q;
    }

    VraiFaux creerVraiFaux(String enonce, int bonneRep, String TexteSupplementaire) {
        VraiFaux vf = new VraiFaux();
        vf.enonce = enonce;
        vf.reponse = bonneRep;
        vf.TexteSupplementaire = TexteSupplementaire;
        return vf;
    }

    boolean attaqueAleatoire(int niveauDifficulte){
        boolean succesAttaque;
        int succes = (int)(random()*(1+(0.125 * (niveauDifficulte-1)))+ 0.5 );
        return (succes >= 1);
    }
}