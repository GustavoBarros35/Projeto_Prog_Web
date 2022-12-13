package br.edu.uepb.diario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

import br.edu.uepb.diario.domain.Professor;
import br.edu.uepb.diario.exceptions.ExistingObjectSameNameException;
import br.edu.uepb.diario.repositories.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	public List<Professor> ListarProfessor (){
		return professorRepository.findAll();
	}

	public Professor adicionarProfessor(Professor professor) throws ExistingObjectSameNameException{
		if (professorRepository.findByNome(professor.getNome()) != null)
            throw new ExistingObjectSameNameException("Já existe um professor com esse nome!");
		return professorRepository.save(professor);
	}

	public Professor buscarProfessor(Long professorID) throws NotFoundException{
		return professorRepository.findById(professorID).orElseThrow(() -> new NotFoundException("Não existe um professor com esse id!"));
	}

	public boolean verificarSeProfessorExiste(Long professorID){
		return professorRepository.existsById(professorID);
	}

	public Professor atualizarProfessor(Long id, Professor professor){
		professor.setId(id);

		return professorRepository.save(professor);
	}
	
	public void deletarProfessor(Long professorID){
		Professor professorToDelete = professorRepository.findById(professorID).get();
        professorRepository.delete(professorToDelete);
	}

}

