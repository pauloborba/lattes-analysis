package com.pa.extractor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.pa.database.impl.DatabaseFacade;
import com.pa.entity.Researcher;
import com.pa.entity.Group;
import com.pa.entity.TechnicalProduction;
import com.pa.exception.InvalidPatternFileException;

public class MultipleXMLExtractor {
	public Group lattesExtractor(String groupName, List<InputStream> inputs) throws InvalidPatternFileException {
		Group group = new Group(groupName);
		
		XMLExtractor extractor = new XMLExtractor();

		for (InputStream input : inputs) {
			Researcher curriculo = extractor.lattesExtractor(input);
			group.getCurriculos().add(curriculo);
		}
		
		return group;
	}
	
	public Group saveGroup(Group groupData, boolean overwrite) {
		Group group;
		
		List<Researcher> updatedCurriculos = new ArrayList<Researcher>();
		
		for (Researcher curriculo : groupData.getCurriculos()) {
			Researcher databaseCurriculo = DatabaseFacade.getInstance().getCurriculoById(curriculo.getId());
			
			if (databaseCurriculo != null) {
				if (overwrite) {
					databaseCurriculo.setName(curriculo.getName());
					databaseCurriculo.setLastUpdate(curriculo.getLastUpdate());
					databaseCurriculo.setCountConcludedOrientations(curriculo.getCountConcludedOrientations());
					databaseCurriculo.setCountOnGoingOrientations(curriculo.getCountOnGoingOrientations());
					databaseCurriculo.setTechinicalProduction((ArrayList<TechnicalProduction>) curriculo.getTechnicalProduction());
					databaseCurriculo.setOrientations(curriculo.getOrientations());
					databaseCurriculo.setBooks(curriculo.getBooks());
					databaseCurriculo.setChapters(curriculo.getChapters());
					
					databaseCurriculo.getPublications().clear();
					databaseCurriculo.getPublications().addAll(curriculo.getPublications());
					
					DatabaseFacade.getInstance().updateCurriculo(databaseCurriculo);
					updatedCurriculos.add(databaseCurriculo);
				}
				else {
					// Update with correct curriculo
					updatedCurriculos.add(databaseCurriculo);
				}
			}
			else {
				updatedCurriculos.add(curriculo);
			}
		}
		
		groupData.setCurriculos(updatedCurriculos);
		
		List<Group> allGroups = DatabaseFacade.getInstance().listAllGroups(groupData);
		if (allGroups.isEmpty()) {
			group = DatabaseFacade.getInstance().saveGroup(groupData);
		}
		else {
			group = allGroups.get(0);
			group.setCurriculos(group.getCurriculos());
			DatabaseFacade.getInstance().updateGroup(group);
		}
		
		return group;
	}
	
	public boolean checkCurriculoExistence(List<Researcher> curriculos) {
		boolean exist = false;
		
		for (Researcher curriculo : curriculos) {			
			Researcher databaseCurriculo = DatabaseFacade.getInstance().getCurriculoById(curriculo.getId());
			if (databaseCurriculo != null) {
				exist = true;
				break;
			}
		}
		
		return exist;
	}
}
