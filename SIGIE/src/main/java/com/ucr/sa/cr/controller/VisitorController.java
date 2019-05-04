package com.ucr.sa.cr.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ucr.sa.cr.business.AreaService;
import com.ucr.sa.cr.business.CarreraService;
import com.ucr.sa.cr.business.CursoService;
import com.ucr.sa.cr.business.DocenteService;
import com.ucr.sa.cr.business.NoticiaService;
import com.ucr.sa.cr.business.PlanEstudiosService;
import com.ucr.sa.cr.business.ProjectService;
import com.ucr.sa.cr.business.PublicacionService;
import com.ucr.sa.cr.business.SugerenciaService;
import com.ucr.sa.cr.domain.Carrera;
import com.ucr.sa.cr.domain.Coordinacion;
import com.ucr.sa.cr.domain.Docente;
import com.ucr.sa.cr.domain.FacilidadesCarrera;
import com.ucr.sa.cr.domain.Sugerencia;
import com.ucr.sa.cr.form.SugerenciaForm;

@Controller
public class VisitorController {

	private static final int CODE_CAREER = 6000;
	private static final int CODE_TURRIALBA = 31;
	private static final int CODE_PARAISO = 32;
	private static final int CODE_GUAPILES = 33;
	
	@Autowired
	private CarreraService careerService;
	
	@Autowired
	private PlanEstudiosService studyPlanService;
	
	@Autowired
	private CursoService courseService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private NoticiaService newService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DocenteService teacherService;
	
	@Autowired
	private PublicacionService publicationService;
		
	@Autowired
	private SugerenciaService suggestionSevice;
	
	@RequestMapping("/home")
	public String visitorPage(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		try {
			model.addAttribute("news",newService.getNews(CODE_CAREER)); //OBTENER NOTICIAS AGREGADAS RECIENTEMENTE
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}
		return "visitorPage";
	}
	
	/*******************************************************SHOW SUBMENUS****************************************************/
	@RequestMapping("/aboutIESubmenuShow")
	public String aboutSubmenuShow() {
		return "fragments/aboutIESubmenu :: aboutIESubmenu";
	}
	
	@RequestMapping("/undergraduateStudiesSubmenuShow")
	public String undergraduateStudiesSubmenuShow() {
		return "fragments/undergraduateStudiesSubmenu :: undergraduateStudiesSubmenu";
	}
	
	@RequestMapping("/investigationSubmenuShow")
	public String investigationSubmenuShow() {
		return "fragments/investigationSubmenu :: investigationSubmenu";
	}
	
	@RequestMapping("/socialActionSubmenuShow")
	public String socialActionSubmenuShow() {
		return "fragments/socialActionSubmenu :: socialActionSubmenu";
	}
	
	@RequestMapping("/teachersSubmenuShow")
	public String teachersSubmenuShow() {
		return "fragments/teachersSubmenu :: teachersSubmenu";
	}
	/***********************************************************************************************************************/
	
	/*******************************************************SHOW CONTENT****************************************************/
	@RequestMapping("/showHistory")
	public String showHistory(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Historia");
		String descripcion = "En este momento no se pueden obtener los datos intentelo más tarde";
		try {
			descripcion =  careerService.getCarrera(CODE_CAREER).getHistoriaCarrera();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("information",descripcion);
		
		return "fragments/visitors/showInformationContent :: showInformationContent";
	}
	
	@RequestMapping("/showCareerDescription")
	public String showDescriptionCareer(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		String duracion = "En este momento no se pueden obtener los datos intentelo más tarde";
		String descripcion = "En este momento no se pueden obtener los datos intentelo más tarde";
		String perfilProf = "En este momento no se pueden obtener los datos intentelo más tarde";
		try {
			Carrera career = careerService.getCarrera(CODE_CAREER);
			duracion = career.getDuracionCarerra()+"";
			descripcion =  career.getDescripcionCarrera();
			perfilProf = career.getPerfilProfesional();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("duration",duracion);
		model.addAttribute("description",descripcion);
		model.addAttribute("professionalProfile",perfilProf);
		
		return "fragments/visitors/showDescriptionCareer :: showDescriptionCareer";
	}
	
	@RequestMapping("/showStudyPlan")
	public String showStudyPlan(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		try {
			model.addAttribute("studyPlans",studyPlanService.getAllStudyPlan(CODE_CAREER));
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}
		return "fragments/visitors/showStudyPlan :: showStudyPlan";
	}
	
	@RequestMapping("/showCourses/{code}")
	public String showCourses(Model model, @PathVariable int code, HttpSession session) {
		session.setAttribute("user", "anonymous");
		try {
			if(courseService.getAllCursos(code).size() > 0)
				model.addAttribute("courses", courseService.getAllCursos(code));
			else
				model.addAttribute("alert","notdata");
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}		
		return "fragments/visitors/showCourses :: showCourses";
	}
	
	@RequestMapping("/showAcreditation")
	public String showAcreditation(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Acreditación");
		String descripcion = "En este momento no se pueden obtener los datos intentelo más tarde";
		try {
			descripcion =  careerService.getCarrera(CODE_CAREER).getAcreditacionCarrera();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("information",descripcion);
		return "fragments/visitors/showInformationContent :: showInformationContent";
	}
	
	@RequestMapping("/showFacilitiesTurrialba")
	public String showFacilitiesTurrialba(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Facilidades Carrera Recinto Turrialba");
		try {
			FacilidadesCarrera facilities = careerService.getFacilitiesCareer(CODE_CAREER, CODE_TURRIALBA);
			if(facilities != null)
				model.addAttribute("information", facilities.getDescripcionFacilidades());
			else
				model.addAttribute("alert", "error");
		} catch (SQLException e) {
			model.addAttribute("alert", "error");
		}
		
		return "fragments/visitors/showFacilitiesCareer :: showFacilitiesCareer";
	}
	
	@RequestMapping("/showFacilitiesParaiso")
	public String showFacilitiesParaiso(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Facilidades Carrera Recinto Paraíso");
		try {
			FacilidadesCarrera facilities = careerService.getFacilitiesCareer(CODE_CAREER, CODE_PARAISO);
			if(facilities != null)
				model.addAttribute("information", facilities.getDescripcionFacilidades());
			else
				model.addAttribute("alert", "error");
		} catch (SQLException e) {
			model.addAttribute("alert", "error");
		}
		
		return "fragments/visitors/showFacilitiesCareer :: showFacilitiesCareer";
	}
	
	@RequestMapping("/showFacilitiesGuapiles")
	public String showFacilitiesGuapiles(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Facilidades Carrera Recinto Guápiles");
		try {
			FacilidadesCarrera facilities = careerService.getFacilitiesCareer(CODE_CAREER, CODE_GUAPILES);
			if(facilities != null)
				model.addAttribute("information", facilities.getDescripcionFacilidades());
			else
				model.addAttribute("alert", "error");
		} catch (SQLException e) {
			model.addAttribute("alert", "error");
		}
		
		return "fragments/visitors/showFacilitiesCareer :: showFacilitiesCareer";
	}
	
	@RequestMapping("/showStrategicObjectives")
	public String showStrategicObjectives(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		String mision = "En este momento no se pueden obtener los datos intentelo más tarde";
		String vision = "En este momento no se pueden obtener los datos intentelo más tarde";
		String objectives = "En este momento no se pueden obtener los datos intentelo más tarde";
		try {
			Carrera career = careerService.getCarrera(CODE_CAREER);
			objectives =  career.getObjetivosCarrera();
			mision =  career.getMisionCarrera();
			vision =  career.getVisionCarrera();
			
			if(objectives.isEmpty())
				objectives = "En este momento no se pueden obtener los datos intentelo más tarde";
			if(mision.isEmpty())
				mision = "En este momento no se pueden obtener los datos intentelo más tarde";
			if(vision.isEmpty())
				vision = "En este momento no se pueden obtener los datos intentelo más tarde";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("mision", mision);
		model.addAttribute("vision", vision);
		model.addAttribute("objectives", objectives);
		return "fragments/visitors/showStrategicObjective :: showStrategicObjective";
	}
	
	@RequestMapping("/showUbicationTurrialba")
	public String showUbicationTurrialba(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Recinto Turrialba, UCR");
		return "fragments/visitors/showUbicationEnclosure :: showUbicationEnclosure";
	}
	
	@RequestMapping("/showUbicationParaiso")
	public String showUbicationParaiso(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Recinto Paraíso, UCR");
		return "fragments/visitors/showUbicationEnclosure :: showUbicationEnclosure";
	}
	
	@RequestMapping("/showUbicationGuapiles")
	public String showUbicationGuapiles(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Recinto Guápiles, UCR");
		return "fragments/visitors/showUbicationEnclosure :: showUbicationEnclosure";
	}
	
	@RequestMapping("/showUndergraduateStudies")
	public String showUndergraduateStudies(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Estudios de Pregrado");
		model.addAttribute("information", "Esta sección aún no se encuentra implementada en el sistema");
		return "fragments/visitors/showInformationContent :: showInformationContent";
	}
	
	@RequestMapping("/showInvestigation")
	public String showInvestigation(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Área de Investigación");
		String descripcion = "En este momento no se pueden obtener los datos intentelo más tarde";
		try {
			descripcion = areaService.getArea(1).getDescripcion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("information",descripcion);
		return "fragments/visitors/showInformationContent :: showInformationContent";
	}
	
	@RequestMapping("/showCurrentInvestigationProjects")
	public String showCurrentInvestigationProjects(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Proyectos de Investigación");
		try {
			model.addAttribute("proyectos",projectService.getAllCurrentsProjectsForArea(1));
		} catch (SQLException e) {
			model.addAttribute("alert", "error");
		}
		return "fragments/visitors/showCurrentProjects :: showCurrentProjects";
	}
	
	@RequestMapping("/showOldInvestigationProjects")
	public String showOldInvestigationProjects(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Proyectos de Investigación");
		try {
			model.addAttribute("proyectos",projectService.getAllOldsProjectsForArea(1));
		} catch (SQLException e) {
			model.addAttribute("alert", "error");
		}
		return "fragments/visitors/showCurrentProjects :: showCurrentProjects";
	}
	
	@RequestMapping("/showSocialAction")
	public String showSocialAction(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Área de Acción Social");
		String descripcion = "En este momento no se pueden obtener los datos intentelo más tarde";
		try {
			descripcion = areaService.getArea(2).getDescripcion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("information",descripcion);
		return "fragments/visitors/showInformationContent :: showInformationContent";
	}
	
	@RequestMapping("/showCurrentSocialActionProjects")
	public String showCurrentSocialActionProjects(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Proyectos de Acción Social (Concluidos)");
		try {
			model.addAttribute("proyectos",projectService.getAllCurrentsProjectsForArea(2));
		} catch (SQLException e) {
			model.addAttribute("alert", "error");
		}
		return "fragments/visitors/showCurrentProjects :: showCurrentProjects";
	}
	
	@RequestMapping("/showOldSocialActionProjects")
	public String showOldSocialActionProjects(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title", "Proyectos de Acción Social");
		try {
			model.addAttribute("proyectos",projectService.getAllOldsProjectsForArea(2));
		} catch (SQLException e) {
			model.addAttribute("alert", "error");
		}
		return "fragments/visitors/showCurrentProjects :: showCurrentProjects";
	}
	
	@RequestMapping("/showCoordinators")
	public String showCoordinators(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		try {
			List<Coordinacion> list = teacherService.getAllTeacherCoordination();
			if(list.isEmpty())
				model.addAttribute("alert","notdata");
			else
				model.addAttribute("coordinators", list);
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}
		return "fragments/visitors/showCoordinators :: showCoordinators";
	}
	
	@RequestMapping("/showTeacher/{code}")
	public String showTeacher(Model model, @PathVariable int code, HttpSession session) {
		session.setAttribute("user", "anonymous");
		try {
			Docente teacher = teacherService.getTeacher(code);
			model.addAttribute("teacher",teacher);
			model.addAttribute("publications", publicationService.getPublicationsForTeacher(teacher.getCod_docente()));
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}
		return "fragments/visitors/showTeacher :: showTeacher";
	}
		
	@RequestMapping("/showTurrialbasTeachers")
	public String showTurrialbasTeachers(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title","Docentes Recinto Turrialba");
		try {
			List<Docente> list = teacherService.getTeachersForEnclosure(CODE_TURRIALBA);
			if(list.isEmpty())
				model.addAttribute("alert","notdata");
			else
				model.addAttribute("teachers", list);
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}
		return "fragments/visitors/showPersonal :: showPersonal";
	}
	
	@RequestMapping("/showParaisosTeachers")
	public String ParaisosTeachers(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title","Docentes Recinto Paraíso");
		try {
			List<Docente> list = teacherService.getTeachersForEnclosure(CODE_PARAISO);
			if(list.isEmpty())
				model.addAttribute("alert","notdata");
			else
				model.addAttribute("teachers", list);
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}
		return "fragments/visitors/showPersonal :: showPersonal";
	}
	
	@RequestMapping("/showGuapilesTeachers")
	public String showGuapilesTeachers(Model model,HttpSession session) {
		session.setAttribute("user", "anonymous");
		model.addAttribute("title","Docentes Recinto Guápiles");
		try {
			List<Docente> list = teacherService.getTeachersForEnclosure(CODE_GUAPILES);
			if(list.isEmpty())
				model.addAttribute("alert","notdata");
			else
				model.addAttribute("teachers", list);
		} catch (SQLException e) {
			model.addAttribute("alert","error");
		}
		return "fragments/visitors/showPersonal :: showPersonal";
	}
	
	@RequestMapping("/showNews")
	public String showNews(Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		
		return "";
	}
	
	@RequestMapping(value="/showSuggestionForm", method=RequestMethod.GET)
	public String showSuggestionForm( SugerenciaForm suggestionForm, Model model, HttpSession session) {
		session.setAttribute("user", "anonymous");
		return "fragments/visitors/showSuggestionForm :: showSuggestionForm";
	}
	
	@RequestMapping(value = "/sendSuggestion", method = RequestMethod.POST)
	public String sendSuggestion(Model model, @Valid SugerenciaForm suggestionForm, BindingResult bindingResult, HttpSession session) {
		session.setAttribute("user", "anonymous");
		if (bindingResult.hasErrors()) {
			model.addAttribute("alert", "error");
			return "fragments/visitors/showSuggestionForm :: showSuggestionForm";
		}else{
			Sugerencia suggestion = new Sugerencia();
			BeanUtils.copyProperties(suggestionForm, suggestion);
			try {
				suggestionSevice.saveSugerencia(suggestion);
				model.addAttribute("alert", "sucess");
			} catch (SQLException e) {
				model.addAttribute("alert", "error");
			}
		}
		model.addAttribute("content", "suggestion");
		return "visitorPage";
	}
	/***********************************************************************************************************************/
	
	@RequestMapping(value = "/login")
	public String login(Model model, HttpSession session) {
		if(session.getAttribute("user") != null){
			if (session.getAttribute("user").toString().equals("anonymous")) {
				return "login";
			}
			return "redirect:/homeAdministrator";
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}
	
}//FVISITORCONTROLLER
