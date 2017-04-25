package com.pa.associator;

import java.util.Map;

import com.pa.entity.Publication;
import com.pa.entity.PublicationType;
import com.pa.entity.Qualis;
import com.pa.entity.QualisData;
import com.pa.util.EnumPublicationLocalType;
import com.pa.util.EnumQualisClassification;
import com.pa.util.LattesAnalysisUtil;

public class QualisAssociatorService {

	private static QualisAssociatorService _instance = null;
	private static final Double PERCENTAGE_SIMILARITY_CONFERENCE_NAME = 0.60;
	
	private QualisAssociatorService() {}
	
	public static QualisAssociatorService getInstance() {
		if(_instance == null) {
			_instance = new QualisAssociatorService();
		}
		
		return _instance;
	}
	
	public void associatePublicationQualis(Publication publication, Map<EnumPublicationLocalType, QualisData> qd) {
		EnumQualisClassification qualisClassification = getQualisForPublication(publication, qd);
		
		publication.setQualis(qualisClassification);
	}

	public EnumQualisClassification getQualisForPublication(Publication publication, Map<EnumPublicationLocalType, QualisData> qualisDataMap) {
		EnumQualisClassification qualisClassification = EnumQualisClassification.NONE;
		
		PublicationType publicationType = publication.getPublicationType();
		
		if (publicationType != null) {
			EnumPublicationLocalType publicationLocalType = publicationType.getType();
			
			if(qualisDataMap != null && qualisDataMap.containsKey(publicationLocalType)) {
				QualisData qualisData = qualisDataMap.get(publicationLocalType);
				
				if(qualisData != null && qualisData.getQualis() != null && !qualisData.getQualis().isEmpty()) {
					for (Qualis qualis : qualisData.getQualis()) {
						if(qualis.getName().equals(publication.getPublicationType().getName())) {
							qualisClassification = qualis.getClassification();
							break;
						}
						else if (LattesAnalysisUtil.computeStringSimilarity(qualis.getName(),publication.getPublicationType().getName()) >= PERCENTAGE_SIMILARITY_CONFERENCE_NAME) {
							qualisClassification = qualis.getClassification();
							break;
						}
					}
				}
			}
		}
		
		return qualisClassification;
	}
	
}
