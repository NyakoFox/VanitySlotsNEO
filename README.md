# VanitySlots

## What is it?

This mod adds four extra armor slots which let you display vanity armor instead
of your equipped armor.

If you want to hide your armor, you can craft the "Familiar" set of armor by
surrounding leather armor with eight glowstone dust in a crafting table.

## How do I blacklist certain items from vanity armor?

Sometimes incompatibilities happen. If something doesn't work properly, open an issue! But you can work around it yourself by adding things to the tag located at:

`data/vanityslots/tags/items/vanity_blacklist.json`

For example, to blacklist the Elytra:

```json
{
  "replace": false,
  "values": [
    "minecraft:elytra"
  ]
}
```

## Download?

Modrinth: https://modrinth.com/mod/vanity

Curseforge: https://www.curseforge.com/minecraft/mc-mods/vanityslots
