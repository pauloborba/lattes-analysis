package com.pa.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import com.pa.database.impl.DatabaseFacade;
import com.pa.entity.Researcher;
import com.pa.entity.Group;
import com.pa.exception.InvalidPatternFileException;
import com.pa.extractor.MultipleXMLExtractor;
 
@ManagedBean(name="createGroupBean")
@ViewScoped
public class CreateGroupBean implements Serializable{
     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean existingGroup = false;
	private String groupName;
	private List<String> fileNames = new ArrayList<String>();
	private List<InputStream> inputFiles = new ArrayList<InputStream>();

	private DualListModel<Group> groups;
	     
    @PostConstruct
    public void init() {
    	List<Group> groupsTarget = new ArrayList<Group>();
        List<Group> groupsFromDatabase = DatabaseFacade.getInstance().listAllGroups();
         
        groups = new DualListModel<Group>(groupsFromDatabase, groupsTarget);
    }
    
    public void restart() {
    	init();
    	groupName = null;
    	fileNames = new ArrayList<String>();
    	inputFiles = new ArrayList<InputStream>();
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException {
    	InputStream newInput = event.getFile().getInputstream();
    	
    	if (!inputFiles.contains(newInput)) {
    		inputFiles.add(newInput);
    		fileNames.add(FilenameUtils.getName(event.getFile().getFileName()));
		}
    }
    
    public void create() {
    	MultipleXMLExtractor extractor = new MultipleXMLExtractor();
    	FacesContext context = FacesContext.getCurrentInstance();
        
    	if(!existingGroup) {
    		if(inputFiles.size() > 0) {
    			createGroupFromFiles(extractor, context);
    		}
    		else {
    			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione ao menos um arquivo de curr�culo v�lido", null) );
    		}
    	}
    	else {
    		List<Group> selectedGroups = this.getGroups().getTarget();
			if(selectedGroups.size() > 0) {
				createGroupFromSelectedGroups(context, selectedGroups);
    		}
    		else {
    			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione ao menos um grupo pr�-existente.", null) );
    		}
    	}
    	
	}

	private void createGroupFromSelectedGroups(FacesContext context,
			List<Group> selectedGroups) {
		List<Researcher> allCurriculos = new ArrayList<Researcher>();
		Set<Researcher> duplicatedCurriculos = new HashSet<Researcher>();
		fillCurriculumLists(selectedGroups, duplicatedCurriculos, allCurriculos);
		
		if(!duplicatedCurriculos.isEmpty()) {
		//	String msg = buildMessageForDuplications(duplicatedCurriculos);
		//	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null) );
		}
		else {
			createGroupFromAnotherGroups(context, allCurriculos);
		}
	}

	private void createGroupFromFiles(MultipleXMLExtractor extractor,
			FacesContext context) {
		try {
			Group group = extractor.lattesExtractor(groupName, inputFiles);
			if (group.getCurriculos().size() > 1 && extractor.checkCurriculoExistence(group.getCurriculos())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um curr�culo contido no grupo j� est� cadastrado na base de dados. Por favor, retire-o da lista e tente novamente.", null) );
			}
			else {
				Group grupoAux = this.save(group, false);
				
				if(grupoAux != null) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O grupo " + grupoAux.getName() + " foi criado com sucesso.", null) );
					restart();
				}
				else {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,  "Um erro de sistema ocorreu ao criar o grupo. Contacte o administrador", null) );
				}
			}
		} catch (InvalidPatternFileException e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  e.getMessage(), null) );
		}
	}

	private void createGroupFromAnotherGroups(FacesContext context,
			List<Researcher> allCurriculos) {
		Group group = new Group(groupName);
		group.getCurriculos().addAll(allCurriculos);
		
		Group grupoAux = this.save(group, true);

		if(grupoAux != null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O grupo " + grupoAux.getName() + " foi criado com sucesso.", null) );
			restart();
		}
		else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,  "Um erro de sistema ocorreu ao criar o grupo. Contacte o administrador", null) );
		}
	}

	private String buildMessageForDuplications(
			Set<Researcher> duplicatedCurriculos) {
		String msg = "";

		if(duplicatedCurriculos.size() == 1) {
			msg = "O curr�culo de: " + duplicatedCurriculos.iterator().next().getName() + " est� contido em mais de um grupo selecionado. Para evitar duplica��es, selecione grupos que n�o tenham curr�culos em comum";
		}
		else {
			msg = "Os curr�culos de ";
			String names = "";
			
			Iterator<Researcher> duplicatedCurriculosIterator = duplicatedCurriculos.iterator();
			
			while(duplicatedCurriculosIterator.hasNext()) {
				names += "\"" + duplicatedCurriculosIterator.next().getName() + "\"";
				
				if(duplicatedCurriculosIterator.hasNext()) {
					names += ", ";
				}
			}
			
			msg += names + " est�o contidos em mais de um grupo selecionado. Para evitar duplica��es, selecione grupos que n�o tenham curr�culos em comum.";
		}
		return msg;
	}

	private void fillCurriculumLists(List<Group> selectedGroups, Set<Researcher> duplicatedCurriculos, List<Researcher> allCurriculos) {
		Iterator<Group> groupIterator = (Iterator<Group>) selectedGroups.iterator();		
		
		while (groupIterator.hasNext()) {
			Group group = groupIterator.next();
			
			for (Researcher curriculo : group.getCurriculos()) {
				if(allCurriculos.contains(curriculo)) {
					duplicatedCurriculos.add(curriculo);
				}
				else {
					allCurriculos.add(curriculo);
				}
			}
		}
	}
    
    public Group save(Group group, boolean overwrite) {
    	MultipleXMLExtractor extractor = new MultipleXMLExtractor();
    	Group savedGroup = extractor.saveGroup(group, overwrite);
    	
    	return savedGroup;
    }
	
	public boolean isExistingGroup() {
		return existingGroup;
	}

	public void setExistingGroup(boolean isExistingGroup) {
		this.existingGroup = isExistingGroup;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public DualListModel<Group> getGroups() {
		return groups;
	}

	public void setGroups(DualListModel<Group> groupsDualList) {
		this.groups = groupsDualList;
	}     

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileName) {
		this.fileNames = fileName;
	}

	public List<InputStream> getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(List<InputStream> inputFiles) {
		this.inputFiles = inputFiles;
	}
}