# MegaWallsFFA Plugin

MegaWallsFFA is a fast-paced, free-for-all PvP minigame for Spigot servers, inspired by the popular Mega Walls game. Players can choose from a variety of kits, each with unique abilities, and fight to be the last one standing.

## Features

*   **Kit System:** Choose from a variety of kits, each with unique items and skills.
*   **Skill System:** Use XP to activate powerful skills and gain an advantage in battle.
*   **In-Game Shop:** Earn coins by killing other players and use them to purchase new kits.
*   **Player Data Storage:** All your stats, coins, and unlocked kits are saved to a MySQL database.
*   **XP System:** Gain experience from kills to level up and use your skills.
*   **Highly Configurable:** Customize the plugin to your liking with the `config.yml` file.

## Commands & Permissions

| Command          | Description                     | Permission             |
| ---------------- | ------------------------------- | ---------------------- |
| `/shop`          | Opens the kit shop.             | (none)                 |
| `/stats`         | Shows your stats.               | (none)                 |
| `/megawallsffa`  | Admin command for MegaWallsFFA. | `megawallsffa.admin`   |

## Configuration

The `config.yml` file allows you to customize various aspects of the plugin.

```yaml
# MegaWallsFFA Configuration

# Database settings
database:
  host: localhost
  port: 3306
  database: megawallsffa
  username: root
  password: "password"

# Game settings
game:
  coins-per-kill: 10
  xp-per-kill: 50
```

## Installation

1.  Download the latest release from the [releases page](https://github.com/your-repo/megawallsffa/releases).
2.  Place the JAR file in your server's `plugins` folder.
3.  Restart your server.
4.  Configure the database settings in the `config.yml` file.
5.  Restart your server again to apply the database settings.

## Future Plans

Check out our `FUTURE_KITS.md` file for a list of planned kits and features!
