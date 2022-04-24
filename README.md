# Simple Sweep
Simple Minecraft mod that removes the sweep attack unless the weapon is enchanted with a sweeping enchantment (i.e. Sweeping Edge).

## Config
`whitelist` If this has entries, only the items listed will have their sweep attack blocked. Entries should be in the form of registry name, i.e. 'minecraft:diamond_sword'

`blacklist` _Only used if whitelist is empty._ Entries in this list will be exluded from having their sweep attack blocked. Entries should be in the form of registry name, i.e. 'minecraft:diamond_sword'

`onlyCrouch` Set to true to require the player to be crouching to do the sweep attack, regardless of if the sword has the Sweeping Edge enchantment.

To find the *registry name* of an item, press _F3 + H_ to activate advanced tooltips. Then, hover over any item you wish to see its registry name.

## Issue Reporting
To report an issue, conflict, or feature request, go ahead and use the [Issue](https://github.com/J-Dill/SimpleSweep/issues "Issue Tab") tab above.