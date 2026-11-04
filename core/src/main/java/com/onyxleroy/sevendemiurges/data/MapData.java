package com.onyxleroy.sevendemiurges.data;

import com.onyxleroy.sevendemiurges.world.MapLayerType;

import java.util.HashMap;

public class MapData {
    // Le chemin vers l'image du tileset (ex: "tilesets/dungeon.png")
    public String tilesetPath;

    // Une map qui associe une couche (BACKGROUND, etc.) à sa grille 2D d'entiers
    public HashMap<MapLayerType, int[][]> layers = new HashMap<>();

    // Constructeur vide obligatoire pour que LibGDX puisse recréer l'objet lors du chargement
    public MapData() {
    }
}
