package com.ucr.sa.cr.controller;

import java.sql.SQLException;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ucr.sa.cr.business.AreaService;
import com.ucr.sa.cr.business.CarreraService;
import com.ucr.sa.cr.business.CoordinacionService;
import com.ucr.sa.cr.business.CursoService;
import com.ucr.sa.cr.business.DocenteService;
import com.ucr.sa.cr.business.EmpresaService;
import com.ucr.sa.cr.business.NoticiaService;
import com.ucr.sa.cr.business.PlanEstudiosService;
import com.ucr.sa.cr.business.ProjectService;
import com.ucr.sa.cr.business.PublicacionService;
import com.ucr.sa.cr.business.RecintoService;
import com.ucr.sa.cr.business.StorageService;
import com.ucr.sa.cr.business.SugerenciaService;
import com.ucr.sa.cr.business.UsuarioService;
import com.ucr.sa.cr.domain.Area;
import com.ucr.sa.cr.domain.Carrera;
import com.ucr.sa.cr.domain.Coordinacion;
import com.ucr.sa.cr.domain.Curso;
import com.ucr.sa.cr.domain.Docente;
import com.ucr.sa.cr.domain.FacilidadesCarrera;
import com.ucr.sa.cr.domain.MemoriaReunion;
import com.ucr.sa.cr.domain.PlanEstudio;
import com.ucr.sa.cr.domain.Proyecto;
import com.ucr.sa.cr.domain.Publicacion;
import com.ucr.sa.cr.domain.Recinto;
import com.ucr.sa.cr.domain.TopicoInteres;
import com.ucr.sa.cr.domain.Usuario;
import com.ucr.sa.cr.form.CursoForm;
import com.ucr.sa.cr.form.DocenteForm;
import com.ucr.sa.cr.form.MemoriaReunionForm;
import com.ucr.sa.cr.form.PlanEstudioForm;
import com.ucr.sa.cr.form.ProyectoForm;
import com.ucr.sa.cr.form.PublicationForm;

@Controller
public class AdministratorController {

	private static final int CODE_CAREER = 6000;

	private Docente teacher;
	private Recinto enclosure;
	private Coordinacion coordination;
	private Usuario user;

	@Autowired
	private UsuarioService userService;

	@Autowired
	private DocenteService teacherService;

	@Autowired
	private RecintoService enclosureService;

	@Autowired
	private NoticiaService newService;

	@Autowired
	private SugerenciaService suggestionService;

	@Autowired
	private PublicacionService publicationService;

	@Autowired
	private EmpresaService organizationService;

	@Autowired
	private CarreraService careerService;

	@Autowired
	private PlanEstudiosService studyPlanService;

	@Autowired
	private CursoService courseService;

	@Autowired
	private CoordinacionService coordinationService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private StorageService storageService;

	@RequestMapping(value = "/homeAdministrator", method = RequestMethod.GET)
	public String administratorPage(DocenteForm teacherForm, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				session.setAttribute("teacher", teacher);
				session.setAttribute("coordination", coordination);
				session.setAttribute("enclosure", enclosure);

				try {
					model.addAttribute("news", newService.getNews(CODE_CAREER));
					model.addAttribute("suggestions",
							suggestionService.getSuggestionForEnclosure(enclosure.getCodRecinto()));
				} catch (SQLException e) {
				}

				return "administratorPage";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "/login/check", method = RequestMethod.POST)
	public String checkUser(@RequestParam Map<String, String> requestParams, Model model, HttpSession session) {
		if (session.getAttribute("user") != null) {
			if (session.getAttribute("user").equals("anonymous")) {
				String nick = requestParams.get("username");
				String pass = requestParams.get("password");
				try {
					if (userService.existsUser(nick, pass)) {
						user = userService.getUser(nick, pass);
						teacher = teacherService.getTeacher(user.getDocente());
						coordination = teacherService.getTeacherCoordination(user.getDocente());
						enclosure = enclosureService.getEnclosureCoodination(coordination.getCodCoordinacion());

						session.setAttribute("user", nick);
						session.setAttribute("codeUser", user.getDocente());
						return "redirect:/homeAdministrator";
					} else {
						model.addAttribute("alert", "error");
						return "login";
					} // el usuario no existe
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
				} // try-catch
			}
			return "redirect:/homeAdministrator";
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/******************************************************* PROFILE ****************************************************/
	@RequestMapping(value = "/showProfile", method = RequestMethod.GET)
	public String showProfile(DocenteForm teacherForm, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("teacher", teacher);
				model.addAttribute("enclosure", enclosure);

				return "Profile/showProfile";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String updateProfile(@Valid DocenteForm teacherForm, HttpSession session, BindingResult bindingResult,
			Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				if (bindingResult.hasErrors()) {
					session.setAttribute("user", user.getNombre_usuario());
					model.addAttribute("teacher", teacher);
					model.addAttribute("enclosure", enclosure);
					model.addAttribute("alert", "error");
					return "Profile/showProfile";
				} else {
					Docente teacherTemp = new Docente();
					BeanUtils.copyProperties(teacherForm, teacherTemp);
					try {
						teacher = teacherService.updateTeacher(teacherTemp);
						model.addAttribute("alert", "sucess");
						session.setAttribute("user", user.getNombre_usuario());
						model.addAttribute("teacher", teacher);
						model.addAttribute("enclosure", enclosure);
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
					}
				} // no hay ningún error en el form
				return "Profile/showProfile";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showUserData")
	public String showUserData(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("user", user);
				model.addAttribute("enclosure", enclosure);

				return "Profile/showUserData";
			} // la session existe
		}
		return "login";
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public String updateUser(@RequestParam Map<String, String> requestParams, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				String nick = requestParams.get("username");
				String pass = requestParams.get("password");
				Usuario userTemp = new Usuario(nick, pass);
				try {
					userService.updateUser(userTemp);
					user.setClave_usuario(pass);
					model.addAttribute("alert", "sucess");
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}

				model.addAttribute("user", user);
				model.addAttribute("enclosure", enclosure);

				return "Profile/showUserData";
			} // la session existe
		}
		return "login";
	}

	/********************************************************PUBLICATION USER*******************************************************/
	@RequestMapping(value = "/showPublicationsUser")
	public String showPublicationsUser(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("publications",
							publicationService.getPublicationsForTeacher(teacher.getCod_docente()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/showPublications";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/deletePublicationUser/{code}")
	public String deletePublicationUser(HttpSession session, Model model, @PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				try {
					model.addAttribute("alert", "sucess");
					publicationService.eliminarPublicacion(code);
					model.addAttribute("publications",
							publicationService.getPublicationsForTeacher(teacher.getCod_docente()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/showPublications";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/updatePublicationUser/{code}")
	public String updatePublicationUser(HttpSession session, Model model, @PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("publication", publicationService.getPublicacion(code));
					model.addAttribute("teacher", teacher);
					model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
					return "Profile/updatePublication";
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
					return "Profile/showPublications";
				}
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newTypePublication/{code}")
	public String newTypePublication(HttpSession session, Model model, @RequestParam("typeName") String name,
			@PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					publicationService.savePublicationType(name);
					model.addAttribute("publication", publicationService.getPublicacion(code));
					model.addAttribute("teacher", teacher);
					model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
					model.addAttribute("message", "El tipo de publicación se ha guardado con éxito");
					return "Profile/updatePublication";
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
					return "Profile/showPublications";
				}
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/updatePublication/save", method = RequestMethod.POST)
	public String updatePublication(@Valid PublicationForm publicationForm, HttpSession session, Model model,
			BindingResult bindingResult) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				Publicacion publicationTemp = new Publicacion();
				BeanUtils.copyProperties(publicationForm, publicationTemp);
				try {
					if (bindingResult.hasErrors()) {
						model.addAttribute("message", "Existen campos inválidos");
						model.addAttribute("publication", publicationTemp);
						model.addAttribute("teacher", teacher);
						model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
						return "Profile/updatePublication";
					} else {
						publicationTemp.setCodDocente(teacher.getCod_docente());
						model.addAttribute("publication", publicationService.actualizarPublicacion(publicationTemp));
						model.addAttribute("teacher", teacher);
						model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
						model.addAttribute("alert", "sucess");
						return "Profile/updatePublication";
					}
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
					return "Profile/showPublications";
				}
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newPublicationUser")
	public String newPublicationUser(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/newPublication";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newTypePublication", method = RequestMethod.POST)
	public String newTypePublication(HttpSession session, Model model, @RequestParam("typeName") String name) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					publicationService.savePublicationType(name);
					model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
					model.addAttribute("message", "El tipo de publicación se ha guardado con éxito");
					return "Profile/newPublication";
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
					return "Profile/newPublication";
				}
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newPublicationUser/save", method = RequestMethod.POST)
	public String savePublicationUser(HttpSession session, Model model, @Valid PublicationForm publicationForm,
			BindingResult bindingResult) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				Publicacion publicationTemp = new Publicacion();
				BeanUtils.copyProperties(publicationForm, publicationTemp);
				try {
					if (bindingResult.hasErrors()) {
						model.addAttribute("message", "Existen campos inválidos");
						model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
						return "Profile/newPublication";
					} else {
						publicationTemp.setCodDocente(teacher.getCod_docente());
						publicationService.savePublicacion(publicationTemp);
						model.addAttribute("publicationTypes", publicationService.getPublicationTypes());
						model.addAttribute("alert", "sucess");
						return "Profile/newPublication";
					}
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/newPublication";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/********************************************************TOPIC INTEREST USER****************************************************/
	@RequestMapping(value = "/showInterestTopics")
	public String showInterestTopics(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("topicsUser",
							teacherService.getTopicosInteresForTeacher(teacher.getCod_docente()));
					model.addAttribute("topics",
							teacherService.getTopicosUninterestForTeacher(teacher.getCod_docente()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/showInterestTopics";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/addTopicUser/{code}", method = RequestMethod.GET)
	public String addTopicUser(HttpSession session, Model model, @PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					teacherService.addTopicToTeacher(code, teacher.getCod_docente());
					model.addAttribute("alert", "sucess");
					model.addAttribute("topicsUser",
							teacherService.getTopicosInteresForTeacher(teacher.getCod_docente()));
					model.addAttribute("topics",
							teacherService.getTopicosUninterestForTeacher(teacher.getCod_docente()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/showInterestTopics";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newTopic", method = RequestMethod.POST)
	public String newTopic(@RequestParam Map<String, String> requestParams, HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				TopicoInteres topic = new TopicoInteres(requestParams.get("name"), requestParams.get("descripcion"));
				try {
					teacherService.addTopic(topic);
					model.addAttribute("alert", "sucess");
					model.addAttribute("topicsUser",
							teacherService.getTopicosInteresForTeacher(teacher.getCod_docente()));
					model.addAttribute("topics",
							teacherService.getTopicosUninterestForTeacher(teacher.getCod_docente()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/showInterestTopics";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/deleteTopicUser/{code}", method = RequestMethod.GET)
	public String deleteTopicUser(HttpSession session, Model model, @PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					teacherService.deleteTopicToTeacher(code, teacher.getCod_docente());
					model.addAttribute("message", "El tema se ha quitado de sus intereses");
					model.addAttribute("topicsUser",
							teacherService.getTopicosInteresForTeacher(teacher.getCod_docente()));
					model.addAttribute("topics",
							teacherService.getTopicosUninterestForTeacher(teacher.getCod_docente()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/showInterestTopics";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/deleteTopic/{code}", method = RequestMethod.GET)
	public String deleteTopic(HttpSession session, Model model, @PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					teacherService.deleteTopic(code);
					model.addAttribute("message", "Tema de Intéres eliminado");
					model.addAttribute("topicsUser",
							teacherService.getTopicosInteresForTeacher(teacher.getCod_docente()));
					model.addAttribute("topics",
							teacherService.getTopicosUninterestForTeacher(teacher.getCod_docente()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Profile/showInterestTopics";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/******************************************************* CAREER ****************************************************/
	@RequestMapping(value = "/showHistoryAdmin")
	public String showHistoryAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("career", careerService.getCarrera(CODE_CAREER));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showHistory";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/saveHistory", method = RequestMethod.POST)
	public String saveHistory(HttpSession session, Model model, @RequestParam("historia") String history) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					Carrera career = careerService.getCarrera(CODE_CAREER);
					career.setHistoriaCarrera(history);
					careerService.updateHistoryCareer(career);
					model.addAttribute("career", careerService.getCarrera(CODE_CAREER));
					model.addAttribute("alert", "sucess");
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showHistory";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showCareerDescriptionAdmin")
	public String showCareerDescriptionAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("career", careerService.getCarrera(CODE_CAREER));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showDescriptionCareer";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/saveDescription", method = RequestMethod.POST)
	public String saveDescription(HttpSession session, Model model,
			@RequestParam("descripcionCarrera") String description, @RequestParam("perfilProfesional") String profile) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					Carrera career = careerService.getCarrera(CODE_CAREER);
					career.setDescripcionCarrera(description);
					career.setPerfilProfesional(profile);
					careerService.updateDescriptionCareer(career);
					model.addAttribute("career", careerService.getCarrera(CODE_CAREER));
					model.addAttribute("alert", "sucess");
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showDescriptionCareer";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showAcreditationAdmin")
	public String showAcreditationAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("career", careerService.getCarrera(CODE_CAREER));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showAcreditation";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/saveAcreditation", method = RequestMethod.POST)
	public String saveAcreditation(HttpSession session, Model model,
			@RequestParam("acreditacion") String acreditation) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					Carrera career = careerService.getCarrera(CODE_CAREER);
					career.setAcreditacionCarrera(acreditation);
					careerService.updateAcreditationCareer(career);
					model.addAttribute("career", careerService.getCarrera(CODE_CAREER));
					model.addAttribute("alert", "sucess");
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showAcreditation";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showFacilities")
	public String showFacilities(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				int codeEnclosure = enclosure.getCodRecinto();
				try {
					model.addAttribute("facilities", careerService.getFacilitiesCareer(CODE_CAREER, codeEnclosure));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showFacilities";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/saveFacilities", method = RequestMethod.POST)
	public String saveFacilities(HttpSession session, Model model, @RequestParam("facilidades") String facilities) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					FacilidadesCarrera fc = careerService.getFacilitiesCareer(CODE_CAREER, enclosure.getCodRecinto());
					fc.setDescripcionFacilidades(facilities);
					careerService.updateFacilitiesCareer(fc);
					model.addAttribute("facilities",
							careerService.getFacilitiesCareer(CODE_CAREER, enclosure.getCodRecinto()));
					model.addAttribute("alert", "sucess");
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showFacilities";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showStrategicObjectivesAdmin")
	public String showStrategicObjectivesAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("career", careerService.getCarrera(CODE_CAREER));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showObjectiveStrategic";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/saveStrategicObjectives", method = RequestMethod.POST)
	public String saveStrategicObjectives(HttpSession session, Model model, @RequestParam Map<String, String> params) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					Carrera career = careerService.getCarrera(CODE_CAREER);
					career.setObjetivosCarrera(params.get("objetivosCarrera"));
					career.setMisionCarrera(params.get("misionCarrera"));
					career.setVisionCarrera(params.get("visionCarrera"));
					careerService.updateObjectivesCareer(career);
					model.addAttribute("career", career);
					model.addAttribute("alert", "sucess");
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Career/showObjectiveStrategic";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/********************************************************STUDY PLAN****************************************************/
	@RequestMapping(value = "/showStudyPlanAdmin")
	public String showStudyPlanAdmin(HttpSession session, Model model, PlanEstudioForm studyPlanForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("types", studyPlanService.getAllTiposIngresos());
					model.addAttribute("studyPlans", studyPlanService.getAllStudyPlan(CODE_CAREER));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "StudyPlan/showStudiesPlans";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newStudyPlan")
	public String newStudyPlan(HttpSession session, Model model, PlanEstudioForm studyPlanForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("types", studyPlanService.getAllTiposIngresos());
					return "StudyPlan/newStudyPlan";
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
					return "StudyPlan/newStudyPlan";
				}
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/saveStudyPlan", method = RequestMethod.POST)
	public String saveStudyPlan(@Valid PlanEstudioForm studyPlanForm, HttpSession session, BindingResult bindingResult,
			Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				PlanEstudio pe = new PlanEstudio();
				BeanUtils.copyProperties(studyPlanForm, pe);
				if (bindingResult.hasErrors()) {
					model.addAttribute("alert", "error");
					try {
						model.addAttribute("types", studyPlanService.getAllTiposIngresos());
						model.addAttribute("studyPlans", studyPlanService.getAllStudyPlan(CODE_CAREER));
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
					}
					return "StudyPlan/showStudiesPlans";
				} else {
					try {
						pe.setCodCarrera(CODE_CAREER);
						studyPlanService.saveStudyPlan(pe);
						model.addAttribute("alert", "sucess");
						model.addAttribute("types", studyPlanService.getAllTiposIngresos());
						model.addAttribute("studyPlans", studyPlanService.getAllStudyPlan(CODE_CAREER));
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
					}
				}
				return "StudyPlan/showStudiesPlans";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newTypeIncome", method = RequestMethod.POST)
	public String newTypeIncome(HttpSession session, Model model, @RequestParam("typeName") String name,
			@RequestParam("description") String description) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					studyPlanService.addTypeIncome(name, description);
					model.addAttribute("types", studyPlanService.getAllTiposIngresos());
					model.addAttribute("alert", "sucessType");
					return "StudyPlan/newStudyPlan";
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
					return "StudyPlan/newStudyPlan";
				}
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/deleteStudyPlan/{code}", method = RequestMethod.GET)
	public String deleteStudyPlan(HttpSession session, Model model, @PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					studyPlanService.deleteStudyPlan(code);
					model.addAttribute("alert", "sucess");
					model.addAttribute("types", studyPlanService.getAllTiposIngresos());
					model.addAttribute("studyPlans", studyPlanService.getAllStudyPlan(CODE_CAREER));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "StudyPlan/showStudiesPlans";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/updateStudyPlan", method = RequestMethod.POST)
	public String updateStudyPlan(@Valid PlanEstudio studyPlanForm, HttpSession session, BindingResult bindingResult,
			Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				PlanEstudio pe = new PlanEstudio();
				BeanUtils.copyProperties(studyPlanForm, pe);
				if (bindingResult.hasErrors()) {
					model.addAttribute("alert", "error");
					try {
						model.addAttribute("types", studyPlanService.getAllTiposIngresos());
						model.addAttribute("studyPlans", studyPlanService.getAllStudyPlan(CODE_CAREER));
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
					}
					return "StudyPlan/showStudiesPlans";
				} else {
					try {
						pe.setCodCarrera(CODE_CAREER);
						studyPlanService.updateStudyPlan(pe);
						model.addAttribute("alert", "sucess");
						model.addAttribute("types", studyPlanService.getAllTiposIngresos());
						model.addAttribute("studyPlans", studyPlanService.getAllStudyPlan(CODE_CAREER));
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
					}
				}
				return "StudyPlan/showStudiesPlans";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/******************************************************* COURSES ****************************************************/
	@RequestMapping(value = "/showCoursesAdmin/{code}", method = RequestMethod.GET)
	public String showCoursesAdmin(CursoForm courseForm, HttpSession session, Model model, @PathVariable int code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("codePlan", code);
				try {
					model.addAttribute("courses", courseService.getAllCursos(code));
					model.addAttribute("levels", courseService.getAllNivelesCursos());
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "StudyPlan/showCourses";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/saveCourse", method = RequestMethod.POST)
	public String saveCourse(@Valid CursoForm courseForm, HttpSession session, @RequestParam("programaCurso") MultipartFile file, 
			BindingResult bindingResult, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				if (bindingResult.hasErrors()) {
					model.addAttribute("alert", "error");
				} else {
					try {
						Curso course = new Curso();
						BeanUtils.copyProperties(courseForm, course);
						storageService.store(file);
						course.setProgramaCurso(file.getOriginalFilename());
						courseService.saveCurso(course);
						
						model.addAttribute("alert", "sucess");
						model.addAttribute("codePlan", course.getCodPlanEstudio());
						model.addAttribute("courses", courseService.getAllCursos(course.getCodPlanEstudio()));
						model.addAttribute("levels", courseService.getAllNivelesCursos(course.getCodPlanEstudio()));
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
					}
				}
				return "StudyPlan/showCourses";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/deleteCourse", method = RequestMethod.GET)
	public String deleteCourse(HttpSession session, Model model, @RequestParam Map<String, String> params) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					int codePlan = Integer.parseInt(params.get("codePlan"));
					courseService.eliminarCurso(params.get("code"), codePlan);
					model.addAttribute("alert", "sucess");
					model.addAttribute("codePlan", codePlan);
					model.addAttribute("courses", courseService.getAllCursos(codePlan));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "StudyPlan/showCourses";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/updateCourse", method = RequestMethod.POST)
	public String updateCourse(@Valid CursoForm courseForm,@RequestParam("programaCurso") MultipartFile file, 
			HttpSession session, BindingResult bindingResult, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				if (bindingResult.hasErrors()) {
					model.addAttribute("alert", "error");
				} else {
					try {
						Curso course = new Curso();
						BeanUtils.copyProperties(courseForm, course);
						
						Curso courseTemp = courseService.getCurso(course.getSiglaCurso());
						
						// Elimino el documento anterior
						storageService.deleteFile(courseTemp.getProgramaCurso());

						// Almaceno el nuevo documento
						storageService.store(file);
						course.setProgramaCurso(file.getOriginalFilename());

						// Actualizo la BD
						courseService.actualizarCurso(course);

						model.addAttribute("alert", "sucess");
						model.addAttribute("codePlan", course.getCodPlanEstudio());
						model.addAttribute("courses", courseService.getAllCursos(course.getCodPlanEstudio()));
						model.addAttribute("levels", courseService.getAllNivelesCursos(course.getCodPlanEstudio()));
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
					}
				}
				return "StudyPlan/showCourses";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/******************************************************* MEMORIES ****************************************************/
	@RequestMapping(value = "/showMemories")
	public String showMemories(HttpSession session, Model model, MemoriaReunionForm memoryForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("memories",
					coordinationService.getMemoriesCoordination(coordination.getCodCoordinacion()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showMemories";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/deleteMemory", method = RequestMethod.GET)
	public String deleteMemory(HttpSession session, Model model, @RequestParam("code") int code,
			@RequestParam("document") String file, MemoriaReunionForm memoryForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					coordinationService.deleteMemory(code);
					storageService.deleteFile(file);
					model.addAttribute("alert", "sucess");
					model.addAttribute("memories",
					coordinationService.getMemoriesCoordination(coordination.getCodCoordinacion()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showMemories";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/updateMemory", method = RequestMethod.POST)
	public String updateMemory(HttpSession session, Model model, @Valid MemoriaReunionForm memoryForm,
			@RequestParam("documento") MultipartFile file, BindingResult bindingResult) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					if (bindingResult.hasErrors()) {
						model.addAttribute("alert", "error");
					} else {
						MemoriaReunion mr = new MemoriaReunion();
						BeanUtils.copyProperties(memoryForm, mr);
						mr = coordinationService.getMemory(mr.getCodMemoria());

						// Elimino el documento anterior
						storageService.deleteFile(mr.getDocumento());

						// Almaceno el nuevo documento
						storageService.store(file);
						mr.setDocumento(file.getOriginalFilename());

						// Actualizo la BD
						coordinationService.updateMemory(mr);

						model.addAttribute("alert", "sucess");
					}
					model.addAttribute("memories",
					coordinationService.getMemoriesCoordination(coordination.getCodCoordinacion()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showMemories";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/newMemory", method = RequestMethod.POST)
	public String newMemory(HttpSession session, Model model, @Valid MemoriaReunionForm memoryForm,
			@RequestParam("documento") MultipartFile file, BindingResult bindingResult) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					if (bindingResult.hasErrors()) {
						model.addAttribute("alert", "error");
					} else {
						storageService.store(file);
						MemoriaReunion mr = new MemoriaReunion();
						BeanUtils.copyProperties(memoryForm, mr);
						mr.setCoordinacion(coordination.getCodCoordinacion());
						mr.setDocumento(file.getOriginalFilename());
						coordinationService.insertMemory(mr);
						model.addAttribute("alert", "sucess");
					}
					model.addAttribute("memories",
							coordinationService.getMemoriesCoordination(coordination.getCodCoordinacion()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showMemories";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/*****************************************************UNDERGRADUATE STUDIES****************************************************/
	@RequestMapping(value = "/showUndergraduateStudiesAdmin")
	public String showUndergraduateStudiesAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				return "redirect:/homeAdministrator";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/**************************************************** INVESTIGATION ***********************************************************/
	@RequestMapping(value = "/showInvestigationAdmin")
	public String showInvestigationAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("title", "Área Investigación");
				model.addAttribute("area", "investigation");
				try {
					model.addAttribute("information", areaService.getArea("Investigación").getDescripcion());
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showArea";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showCurrentInvestigationProjectsAdmin")
	public String showCurrentInvestigationProjectsAdmin(HttpSession session, Model model, ProyectoForm projectForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("title", "Proyectos de Investigación");
				model.addAttribute("area", "investigation");
				try {
					model.addAttribute("projects", projectService
							.getAllCurrentsProjectsForArea(areaService.getArea("Investigación").getCodArea()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showProjects";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showOldInvestigationProjectsAdmin")
	public String showOldInvestigationProjectsAdmin(HttpSession session, Model model, ProyectoForm projectForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("title", "Proyectos de Investigación (Concluidos)");
				model.addAttribute("area", "investigation");
				try {
					model.addAttribute("projects", projectService
							.getAllOldsProjectsForArea(areaService.getArea("Investigación").getCodArea()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showProjects";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/*****************************************************SOCIAL ACTION***********************************************************/
	@RequestMapping(value = "/showSocialActionAdmin")
	public String showSocialActionAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("title", "Área Acción Social");
				model.addAttribute("area", "socialAction");
				try {
					model.addAttribute("information", areaService.getArea("Acción Social").getDescripcion());
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showArea";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showCurrentSocialActionProjectsAdmin")
	public String showCurrentSocialActionProjectsAdmin(HttpSession session, Model model, ProyectoForm projectForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("title", "Proyectos de Acción Social");
				model.addAttribute("area", "socialAction");
				try {
					model.addAttribute("projects", projectService
							.getAllCurrentsProjectsForArea(areaService.getArea("Acción Social").getCodArea()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showProjects";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showOldSocialActionProjectsAdmin")
	public String showOldSocialActionProjectsAdmin(HttpSession session, Model model, ProyectoForm projectForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				model.addAttribute("title", "Proyectos de Acción Social (Concluidos)");
				model.addAttribute("area", "socialAction");
				try {
					model.addAttribute("projects", projectService
							.getAllOldsProjectsForArea(areaService.getArea("Acción Social").getCodArea()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showProjects";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	/****************************************************CRUD PROJECTS***********************************************************/
	@RequestMapping(value = "/saveAreaDescription/{area}")
	public String saveAreaDescription(HttpSession session, Model model, @RequestParam("description") String description,
			@PathVariable String area) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					Area areaE;
					if (area.equals("investigation")) {
						areaE = areaService.getArea("Investigación");
						model.addAttribute("title", "Área Investigación");
						model.addAttribute("area", "investigation");
					} else {
						model.addAttribute("title", "Área Acción Social");
						model.addAttribute("area", "socialAction");
						areaE = areaService.getArea("Acción Social");
					}
					areaE.setDescripcion(description);
					areaService.updateArea(areaE);
					model.addAttribute("information", areaE.getDescripcion());
					model.addAttribute("alert", "sucess");
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showArea";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/deleteCurrentProject", method = RequestMethod.GET)
	public String deleteCurrentProject(HttpSession session, Model model, @RequestParam("area") String area,
			@RequestParam("code") String code, ProyectoForm projectForm) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					Area areaE;
					if (area.equals("investigation")) {
						areaE = areaService.getArea("Investigación");
						model.addAttribute("title", "Proyectos de Investigación");
						model.addAttribute("area", "investigation");
					} else {
						model.addAttribute("title", "Proyectos de Acción Social");
						model.addAttribute("area", "socialAction");
						areaE = areaService.getArea("Acción Social");
					}
					model.addAttribute("alert", "sucess");
					projectService.deleteProject(code);
					model.addAttribute("projects", projectService.getAllCurrentsProjectsForArea(areaE.getCodArea()));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showProjects";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}
	
	@RequestMapping(value = "/showProject", method = RequestMethod.GET)
	public String showProject(HttpSession session, Model model, @RequestParam("area") String area,
			@RequestParam("code") String code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					Area areaE;
					if (area.equals("investigation")) {
						areaE = areaService.getArea("Investigación");
						model.addAttribute("title", "Proyecto de Investigación");
						model.addAttribute("area", "investigation");
					} else {
						model.addAttribute("title", "Proyecto de Acción Social");
						model.addAttribute("area", "socialAction");
						areaE = areaService.getArea("Acción Social");
					}
					model.addAttribute("project", projectService.getProject(code));
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
					e.printStackTrace();
				}
				return "Coordination/showProjectDetail";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}
	
	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public String saveProject(HttpSession session, Model model, @RequestParam("area") String area,@Valid ProyectoForm projectForm,
			BindingResult bindingResult) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					if (bindingResult.hasErrors()) {
						model.addAttribute("alert", "error");
					} else {
						Area areaE;
						if (area.equals("investigation")) {
							areaE = areaService.getArea("Investigación");
							model.addAttribute("title", "Proyectos de Investigación");
							model.addAttribute("area", "investigation");
						} else {
							model.addAttribute("title", "Proyectos de Acción Social");
							model.addAttribute("area", "socialAction");
							areaE = areaService.getArea("Acción Social");
						}
						Proyecto project = new Proyecto();
						BeanUtils.copyProperties(projectForm, project);
						project.setArea(areaE.getCodArea());
						model.addAttribute("alert", "sucess");
						projectService.saveProject(project);
						model.addAttribute("project", projectService.getProject(project.getCodProyecto()));
						}
					} catch (SQLException e) {
						model.addAttribute("alert", "error");
						e.printStackTrace();
						return "Coordination/showProjects";
				}
				return "Coordination/showProjectDetail";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}
	
	@RequestMapping(value = "/updateProject/{code}", method = RequestMethod.GET)
	public String updateProject(HttpSession session, Model model, @PathVariable String code) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				
				return "Coordination/showProjectDetail";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	
	@RequestMapping(value = "/showTeachersAdmin")
	public String showTeachersAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				return "";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showCoordinatorsAdmin")
	public String showCoordinatorsAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				return "";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showActivesTeachers")
	public String showActivesTeachers(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				return "";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showInactivesTeachers")
	public String showInactivesTeachers(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				return "";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showNewsAdmin")
	public String showNewsAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());

				return "";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showOrganizationsAdmin")
	public String showOrganizationsAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {
				session.setAttribute("user", user.getNombre_usuario());
				try {
					model.addAttribute("organizations", organizationService.getOrganizations());
				} catch (SQLException e) {
					model.addAttribute("alert", "error");
				}
				return "Organization/showOrganizations";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}

	@RequestMapping(value = "/showSuggestionAdmin")
	public String showSuggestionAdmin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			if (!session.getAttribute("user").equals("anonymous")) {

				return "";
			} // la session existe
		}
		session.setAttribute("user", "anonymous");
		return "login";
	}
	/***********************************************************************************************************************/

}// ADMINISTRATOR CONTROLLER
