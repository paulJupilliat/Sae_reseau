format 224

classcanvas 128002 class_ref 128002 // Salon
  classdiagramsettings member_max_width 0 end
  xyz 73 45 2000
end
classcanvas 128130 class_ref 128130 // Client
  classdiagramsettings member_max_width 0 end
  xyz 79 116 2000
end
classcanvas 128258 class_ref 128258 // ClientEnvoyer
  classdiagramsettings member_max_width 0 end
  xyz 8 348 2006
end
classcanvas 128514 class_ref 128386 // ClientRecevoir
  classdiagramsettings member_max_width 0 end
  xyz 303 332 3005
end
classcanvas 128770 class_ref 128514 // LancerC
  classdiagramsettings member_max_width 0 end
  xyz 387 226 2000
end
classcanvas 128898 class_ref 128642 // Serveur
  classdiagramsettings member_max_width 0 end
  xyz 689 183 2000
end
classcanvas 129026 class_ref 128770 // ServeurEcouter
  classdiagramsettings member_max_width 0 end
  xyz 585 418 3011
end
classcanvas 129282 class_ref 128898 // ServeurEnvoyer
  classdiagramsettings member_max_width 0 end
  xyz 270 29 3016
end
classcanvas 129410 class_ref 129026 // Tuple
  classdiagramsettings member_max_width 0 end
  xyz 559 37 3022
end
relationcanvas 128386 relation_ref 128130 // <unidirectional association>
  from ref 128130 z 2007 to ref 128258
  role_a_pos 150 330 3000 no_role_b
  no_multiplicity_a no_multiplicity_b
end
relationcanvas 128642 relation_ref 128258 // <unidirectional association>
  from ref 128130 z 3006 to ref 128514
  role_a_pos 310 323 3000 no_role_b
  no_multiplicity_a no_multiplicity_b
end
relationcanvas 129154 relation_ref 128514 // <unidirectional association>
  from ref 129026 z 3012 to ref 128898
  role_a_pos 787 333 3000 no_role_b
  no_multiplicity_a no_multiplicity_b
end
relationcanvas 129538 relation_ref 128898 // <unidirectional association>
  from ref 129410 z 3023 to ref 129026
  role_a_pos 771 400 3000 no_role_b
  no_multiplicity_a no_multiplicity_b
end
relationcanvas 129666 relation_ref 129026 // <unidirectional association>
  from ref 129410 z 3023 to ref 129282
  role_a_pos 540 67 3000 no_role_b
  no_multiplicity_a no_multiplicity_b
end
relationcanvas 129794 relation_ref 129154 // <unidirectional association>
  from ref 128898 z 3023 stereotype "<<Map>>" xyz 743.5 154.5 3023 to ref 129410
  role_a_pos 775 133 3000 no_role_b
  no_multiplicity_a no_multiplicity_b
end
end
