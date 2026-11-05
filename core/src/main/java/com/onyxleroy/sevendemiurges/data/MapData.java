package com.onyxleroy.sevendemiurges.data;

import com.onyxleroy.sevendemiurges.world.MapLayerType;

import java.util.EnumMap;
import java.util.HashMap;

public class MapData {
    // Le chemin vers l'image du tileset (ex: "tilesets/dungeon.png")
    public String tilesetPath;

    // Une map qui associe une couche (BACKGROUND, etc.) à sa grille 2D d'entiers
    public EnumMap<MapLayerType, int[][]> layers = new EnumMap<>(MapLayerType.class);

    // Constructeur vide obligatoire pour que LibGDX puisse recréer l'objet lors du chargement
    public MapData() {
    }
}
