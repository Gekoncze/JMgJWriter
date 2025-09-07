package cz.mg.java.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.java.entities.JStructure;

public @Service interface JStructureWriter<T extends JStructure> {
    @Mandatory List<String> write(@Mandatory T structure);
}
