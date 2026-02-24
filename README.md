> [!WARNING]
> **This plugin is no longer maintained.**
> It may not work with recent versions and is **not recommended for new projects**.  
> Consider not using it. It will be archived

# EminiumGamesSecurities

[English version below](#english-version)

## Français

Plugin de sécurité pour serveur Minecraft qui oblige les administrateurs à s'authentifier avant de pouvoir effectuer des actions dans le jeu.

### Fonctionnalités

- Authentification obligatoire pour les joueurs avec la permission `security.admin`
- Blocage complet des actions (mouvement, interaction, dégâts) pour les admins non authentifiés
- Protection contre l'utilisation de commandes sans authentification
- Configuration facile via le fichier `config.yml`

### Installation

1. Téléchargez la dernière version du plugin depuis [les releases GitHub](https://github.com/Eminium-Games/EminiumGamesSecurities/releases)
2. Placez le fichier `EminiumGamesSecurities-1.0.jar` dans le dossier `plugins/` de votre serveur
3. Redémarrez le serveur
4. Configurez le mot de passe dans `plugins/EminiumGamesSecurities/config.yml`

### Configuration

Le fichier `config.yml` contient les paramètres suivants :

```yaml
# Mot de passe admin (ne pas utiliser d'espaces)
admin-password: "MotDePasse1234!"
```

> **Important** : Pensez bien à changer le mot de passe par défaut !

### Utilisation

1. **Pour les administrateurs :**
   - Connectez-vous avec `/admin <motdepasse>`
   - Déconnectez-vous avec `/adminlogout`

2. **Pour les joueurs normaux :**
   - Aucun changement, fonctionne normalement

### Développement

Pour compiler le plugin :

```bash
mvn clean package
```

Le fichier JAR sera généré dans le dossier `target/`.

---

## English Version

Minecraft server security plugin that requires administrators to authenticate before performing in-game actions.

### Features

- Mandatory authentication for players with the `security.admin` permission
- Complete action blocking (movement, interaction, damage) for unauthenticated admins
- Protection against command usage without authentication
- Easy configuration via `config.yml` file

### Installation

1. Download the latest version from [GitHub releases](https://github.com/Eminium-Games/EminiumGamesSecurities/releases)
2. Place the `EminiumGamesSecurities-1.0.jar` file in your server's `plugins/` folder
3. Restart the server
4. Configure the password in `plugins/EminiumGamesSecurities/config.yml`

### Configuration

The `config.yml` file contains the following settings:

```yaml
# Admin password (do not use spaces)
admin-password: "ChangeThisPassword123!"
```

> **Important**: Remember to change the default password!

### Usage

1. **For administrators:**
   - Log in with `/admin <password>`
   - Log out with `/adminlogout`

2. **For regular players:**
   - No changes, works normally

### Development

To compile the plugin:

```bash
mvn clean package
```

The JAR file will be generated in the `target/` folder.

## License

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus d'informations.
