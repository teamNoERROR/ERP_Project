package kr.co.noerror.Controller;

//@Controller
//public class common_controller {
//	Logger log = LoggerFactory.getLogger(this.getClass());
//	
//	
//	@Resource(name="employee_DTO")
//	employee_DTO emp_dto;
//	
//	@Autowired
//	common_DAO emp_svc;
//	
//	
//	@GetMapping("/employee_list.do")
//	public String emp_list(Model m) {
//		List<employee_DTO> all_data = this.emp_svc.emp_list();  //DB로드
//	
//		m.addAttribute("employees",all_data);
//		
//		return  "/modals/employee_list_modal.html";
//		return  null;
//	}
//
//}
