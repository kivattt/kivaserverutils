# KivaServerUtils

![Example chat](images/chatexample.png)

## Features
- Improved player chat, with colors, nicknames and pronouns aswell as red usernames for OP players\
- Adds spawn and home commands\
- Adds a stricter mob cap which allows mobs to be enabled for servers without lag\
- Improved logging for chests and crates, see items being taken/added\
- Fixes 100% CPU usage on 1 core\
- Explosions (TNT, Creepers, Dynamite etc.) don't break chests/crates. (Configurable!)\
- Improved logging for players throwing dynamite\
- Adds a [restrictive mode](#restrictive-mode) that prevents players from breaking/placing blocks, interacting with chests etc.
- Adds the ability to mute players from chat

## Commands
<details>
<summary>Home</summary>

`/home`\
Teleport to home (If home exists)

`/homewhere`\
Show home location without teleporting to it

`/sethome`\
Sets your home location
</details>

<details>
<summary>Spawn</summary>

`/spawn`\
Teleport to spawn (If spawn location specified)

`/spawnwhere`\
Show spawn location without teleporting to it

`/spawnset` (OP-only command)\
Set the location `/spawn` sends the player

`/spawnreset` (OP-only command)\
Removes spawn location
</details>

<details>
<summary>Nickname</summary>

`/nick <nickname>`\
Give yourself a nickname in chat

`/nicklist`\
See everyone's nicknames

`/nickset <player> <nickname>` (OP-only command)\
Force set a players nickname

`/nickreset <optional player>`\
Resets / removes your nickname\
When player supplied, an OP can force reset a players nickname
</details>

<details>
<summary>Pronouns</summary>

`/pronouns <pronouns>`\
Give yourself pronouns in chat

`/pronounslist`\
See everyone's pronouns

`/pronounsset <player> <pronouns>` (OP-only command)\
Force set a players pronouns

`/pronounsreset <optional player>`\
Resets / removes your pronouns\
When player supplied, an OP can force reset a players pronouns
</details>

<details>
<summary>Name colors</summary>

`/namecolor <color>`\
Change your name color

`/namecolorreset`\
Resets your name color
</details>

<details>
<summary>Chat mute</summary>

`/mute <player>` (OP-only command)\
Toggles preventing the player from sending chat messages\
Commands are still allowed while muted, except for /me and /tell

`/mutelist`\
Lists all muted players
</details>

<details>
<summary>Restrictive mode</summary>

`/restrict <player>` (OP-only command)\
Toggles [restrictive mode](#restrictive-mode)

`/restrictlist` (OP-only command)\
Lists all players in [restrictive mode](#restrictive-mode)

`/restrictbydefault <true or false>` (OP-only command)\
All players will be in [restrictive mode](#restrictive-mode) when `true` (`/restrictlist` is ignored), unless specifically excluded with `/restrictexclude`

`/restrictexclude <player>` (OP-only command)\
Excludes player from restrictive mode when `/restrictbydefault` is true

`/restrictexcludelist` (OP-only command)\
Lists all players excluded from restrictive mode
</details>

<details>
<summary>Configuration</summary>

`/mobcapdisabled <true or false>` (OP-only command)\
Enable or disable the mobcap (setting mobcapdisabled to true will introduce lag!)

`/explosionsbreakchests <true or false>` (OP-only command)\
Set if explosions break chests and crates

`/homecommandsdisabled <true or false>` (OP-only command)\
Enable/disable the `/home` and `/sethome` commands

`/kivashowconfig`\
Shows config for KivaServerUtils
</details>

`/teleport <x> <y> <z>` (OP-only command)\
Teleport to coordinate

`/kivaversion`\
Displays mod version

# Restrictive mode
Players in restrictive mode are prevented from
- Placing & breaking blocks
- Attacking entities or players
- Editing signs
- Opening chests
- Igniting TNT or throwing dynamite

But are allowed to
- Move
- Eat food
- Pick up items
- Be targeted by mobs

Essentially, any right click actions are disallowed except for eating food

# Info for mod developers
`Container.updateInventory` is overwritten by this mod,\
keep this in mind if you are writing mixins for this method

You can control/query [restrictive mode](#restrictive-mode) in your own mods with this line of code (replace `isPlayerInRestrictiveMode` with whatever method you need):
`ModLoader.getModContainer("kivaserverutils").getMod().getClass().getMethod("isPlayerInRestrictiveMode", String.class).invoke(null, "USERNAME GOES HERE");`

# Known issues
Players can spawn randomly around the spawn set by `/spawnset`, so it's not always a perfect coordinate

Doing `/sethome` then covering up the home location with blocks, unloading the chunk its in,
then doing `/home` can make you fall into an underground cave
this will also affect other commands like `/spawn`, since it's really an underlying issue with teleporting

# Other
`/sethome` doesn't follow the naming convention `...set` because it's such a common command name in Minecraft servers

All data KivaServerUtils stores is in the `mods/KivaServerUtils` folder