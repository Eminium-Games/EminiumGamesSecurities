# EminiumGamesSecurities

Plugin de sécurité pour serveur Minecraft qui oblige les administrateurs à s'authentifier avant de pouvoir effectuer des actions dans le jeu.

## Fonctionnalités

- Authentification obligatoire pour les joueurs avec la permission `security.admin`
- Blocage complet des actions (mouvement, interaction, dégâts) pour les admins non authentifiés
- Protection contre l'utilisation de commandes sans authentification
- Configuration facile via le fichier `config.yml`

## Installation

1. Téléchargez la dernière version du plugin depuis [les releases GitHub](https://github.com/votre-utilisateur/EminiumGamesSecurities/releases)
2. Placez le fichier `EminiumGamesSecurities-1.0.jar` dans le dossier `plugins/` de votre serveur
3. Redémarrez le serveur
4. Configurez le mot de passe dans `plugins/EminiumGamesSecurities/config.yml`

## Configuration

Le fichier `config.yml` contient les paramètres suivants :

```yaml
# Mot de passe admin (ne pas utiliser d'espaces)
admin-password: "MotDePasse1234!"
```

Pensez bien à changer le mot de passe !!!

## Utilisation

1. Pour les administrateurs :
   - Connectez-vous avec `/admin <motdepasse>`
   - Déconnectez-vous avec `/adminlogout`

2. Pour les joueurs normaux :
   - Aucun changement, fonctionne normalement

## Développement

Pour compiler le plugin :

```bash
mvn clean package
```

Le fichier JAR sera généré dans le dossier `target/`.

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus d'informations.
