# KivaServerUtils
## Features
Improved player chat, with colors, nicknames and pronouns aswell as red usernames for OP players\
Adds spawn and home commands\
Improved logging for chests and crates, see items being taken/added\
Fixes 100% CPU usage on 1 core\
Explosions (TNT, Creepers, Dynamite etc.) don't break chests/crates.\
Improved logging for players throwing dynamite

![Example chat](images/chatexample.png)

## Commands
`/home`\
Teleport to home (If home exists)

`/sethome`\
Sets your home location

`/spawn`\
Teleport to spawn (If spawn location specified)

`/spawnset` (OP-only command)\
Set the location `/spawn` sends the player

`/nick <nickname>`\
Give yourself a nickname in chat

`/nicklist`\
See everyone's nicknames

`/nickset <player> <nickname>` (OP-only command)\
Force set a players nickname

`/nickreset <optional player>`\
Resets / removes your nickname\
When player supplied, an OP can force reset a players nickname

`/pronouns <pronouns>`\
Give yourself pronouns in chat

`/pronounsset <player> <pronouns>` (OP-only command)\
Force set a players pronouns

`/pronounsreset <optional player>`\
Resets / removes your pronouns\
When player supplied, an OP can force reset a players pronouns

`/teleport <x> <y> <z>` (OP-only command)\
Teleport to coordinate

`/spawnreset` (OP-only command)\
Resets spawn location to null

`/kivaversion`\
Displays mod version

# Known issues/bugs
All teleportation in this mod requires the player to relog,
since they get kicked for moving too fast. This is an issue with
the current version of FoxLoader, and I may manually fix this in a future release.

# Info for mod developers
`Container.updateInventory` is overwritten by this mod,\
keep this in mind if you are writing mixins for this method

# Other
`/sethome` doesn't follow the naming convention `...set` because it's such a common command name in Minecraft servers
