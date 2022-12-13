package br.edu.uepb.diario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

import br.edu.uepb.diario.domain.Aluno;
import br.edu.uepb.diario.domain.Professor;
import br.edu.uepb.diario.domain.Turma;
import br.edu.uepb.diario.exceptions.ExistingObjectSameNameException;
import br.edu.uepb.diario.repositories.TurmaRepository;

@Service
public class TurmaService {
	
	@Autowired
	private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorService professorService;

	@Autowired
    private AlunoService alunoService;
	
	public List<Turma> ListarTurmas (){
		return turmaRepository.findAll();
	}

	public Turma adicionarTurma(Turma turma)throws ExistingObjectSameNameException{
		if (turmaRepository.findByNome(turma.getNome()) != null)
            throw new ExistingObjectSameNameException("Já existe um turma com esse nome!");
		return turmaRepository.save(turma);
	}

	public Turma buscarTurma(Long turmaID) throws NotFoundException{
		return turmaRepository.findById(turmaID).orElseThrow(() -> new NotFoundException("Não existe um turma com esse id!"));
	}

	public boolean verificarSeTurmaExiste(Long turmaID){
		return turmaRepository.existsById(turmaID);
	}

	public Turma atualizarTurma(Turma turmaRecebida){
		Turma turma = turmaRepository.findById(turmaRecebida.getId()).get();

		turma.setNome(turmaRecebida.getNome());
		turma.setSala(turmaRecebida.getSala());
		
		return turmaRepository.save(turma);
	}

	public void deletarTurma(Long turmaID){
		Turma turmaToDelete = turmaRepository.findById(turmaID).get();
        turmaRepository.delete(turmaToDelete);
	}


	
    public Turma vincularProfessor(Long idTurma, Long idProf) throws ExistingObjectSameNameException, NotFoundException{

        Professor professor = professorService.buscarProfessor(idProf);   
		Turma turma = turmaRepository.findById(idTurma).get();

        turma.setProfessor(professor);
        professor.getTurmas().add(turma);

        
        professorService.adicionarProfessor(professor);
        return this.adicionarTurma(turma);
    }

	public Turma matricularAluno(Long idTurma, Long idAluno) throws ExistingObjectSameNameException, NotFoundException{

        Aluno aluno = alunoService.buscarAluno(idAluno);
		Turma turma = turmaRepository.findById(idTurma).get();

        turma.getAlunos().add(aluno);
        aluno.getTurmas().add(turma);

		alunoService.adicionarAluno(aluno);
		return this.adicionarTurma(turma);
		

    }
}
