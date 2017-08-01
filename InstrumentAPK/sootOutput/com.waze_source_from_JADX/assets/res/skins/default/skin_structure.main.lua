--------------------
-- Structure file --
--------------------
--The base map structure, while colors and other paramers sit in another file


--Defaults value for MinZoom in each category can be defined here
MinZoomDefaults {
    color   			= Colors.Defaults.fill,
	thickness    		= 3,
  	border_width 		= 0,
	border_color 		= Colors.Defaults.borders,
	
	label_visibility 	= LabelVisibility.all,		-- Values: all/shield_only/none
	label_size 			= Params.Defaults.font_size,
	label_color 		= Colors.Defaults.labels,
	label_bgcolor   	= Colors.Defaults.labels_bgcolor,
	label_bold 			= false,
	label_uppercase 	= false,

	shield_scale_factor = 1.0,
}

-- General params example
General {
	map_bg_color = Colors.General.map_background,
	map_missing_color = Colors.General.missing,

	-- Alt colors: { ALT_COLOR_1, ALT_COLOR_2, ALT_COLOR_3, CURRENT_ROUTE_COLOR }
	nav_alt_colors = Colors.General.nav_alt_colors,	
	nav_selected_thickness = 10,
	nav_not_selected_thickness = 5,
	
	map_selection_color = Colors.General.map_selection_color,
	map_one_way_color = Colors.General.map_one_way_color,
    map_points_color = Colors.General.map_points_color,

	-- Traffic types:{LIGHT, MODERATE, HEAVY, STAND_STILL, UNUSED, CLOSED_ROAD}
	traffic_route_colors = Colors.General.traffic_route_colors, 
	traffic_arrow_colors = Colors.General.traffic_arrow_colors,
	
	label_shields_max_count = 18, -- Maximum number of shields on screen
    routing_label_size = 15 -- Base size of the routing label
}

FontSizes = {
	huge = 18,
	big = 14,
	medium = 12,
	small = 10,
	tiny = 8,
}

ShieldScaleFactors = {
   big = 1.0,
   medium = 0.8,
   small = 0.6,
}

-- Roads -------------------------------------

Category("Freeways") {
	MinZoom {
		thickness = 20,
		color = Colors.Freeways.strong_fill,
		border_color = Colors.Freeways.strong_stroke,
		border_width = 2,

		label_size = FontSizes.big,
		label_color = Colors.Freeways.strong_label,
	},

	Zoom(20) {
		thickness = 8,
		label_size = FontSizes.medium,		
	},
	
	Zoom(30) {
		thickness = 6,
		border_color = Colors.Freeways.medium_stroke,
	},

	Zoom(60) {
		thickness = 6,
		border_width = 0,
	},

	Zoom(80) {
		thickness = 4,
		label_size = FontSizes.small,
      	shield_scale_factor = ShieldScaleFactors.medium,
	},

	Zoom(300) {
		color = Colors.Freeways.medium_fill,
		label_size = FontSizes.tiny,	
		thickness = 3,
		label_visibility = LabelVisibility.shield_only,
	},

	Zoom(600) {
		label_color = Colors.Freeways.medium_label,
		thickness = 3,
    	shield_scale_factor = ShieldScaleFactors.small,
	},


	Zoom(4000) {
		thickness = 1.7,
		color = Colors.Freeways.light_fill,
	},
	
	LastZoom(9000) {
		thickness = 1.5,
	}
}

Category("Primary") {
	MinZoom {
		thickness = 16,
		color = Colors.Primary.strong_fill,
		border_color = Colors.Primary.strong_stroke,
		border_width = 2,

		label_size = FontSizes.big,
		label_color = Colors.Primary.strong_label,
	},

	Zoom(20) {
		label_size = FontSizes.medium,
	},
	
	Zoom(30) {
		thickness = 5,
		border_width = 1,
	},

	Zoom(80) {
		label_size = FontSizes.small,	
		label_color = Colors.Primary.medium_label,
      	shield_scale_factor = ShieldScaleFactors.medium,
	},

	Zoom(150) {
		color = Colors.Primary.medium_fill,	
		thickness = 4,
		border_width = 0,		
	},

	Zoom(300) {
		label_size = FontSizes.tiny,
		color = Colors.Primary.light_fill,	
		label_visibility = LabelVisibility.shield_only,		
	},

	Zoom(600) {
		thickness = 2.5,
      	shield_scale_factor = ShieldScaleFactors.small,
	},

	Zoom(3000) {
		thickness = 2,
	},
	
	Zoom(5000) {
		thickness = 1.7,
	},

	LastZoom(9000) {
		thickness = 0.5,
	}
}

Category("Secondary") {
	MinZoom {
		thickness = 15,
		color = Colors.Secondary.strong_fill,
		border_color = Colors.Secondary.strong_stroke,
		border_width = 1,

		label_size = FontSizes.big,
		label_color = Colors.Secondary.strong_label,
	},

	Zoom(20) {
		label_size = FontSizes.medium,		
	},

	Zoom(30) {
		thickness = 5,
		border_color = Colors.Secondary.medium_stroke,
      	shield_scale_factor = ShieldScaleFactors.medium,
	},

	Zoom(80) {
		label_size = FontSizes.small,	
		label_color = Colors.Secondary.medium_label,		
	},

	Zoom(150){
		color = Colors.Secondary.medium_fill,
	},

	Zoom(300) {
		label_size = FontSizes.tiny,
		color = Colors.Secondary.light_fill,					
		thickness = 2.5,
		border_width = 0,
		label_visibility = LabelVisibility.shield_only,

	},

	Zoom(400) {
		thickness = 2,
		label_visibility = LabelVisibility.none,
	},

	Zoom(600) {
		thickness = 2,
      	--shield_scale_factor = ShieldScaleFactors.small,
	},

	LastZoom(5000) {
		thickness = 1,
	}
}

-- This is the good beavior when the tile geom scale factor is set to 80

Category("Highways") {
	MinZoom {
		thickness = 17,
		color = Colors.Highways.strong_fill,
		border_color = Colors.Highways.strong_stroke,
		border_width = 1,

		label_size = FontSizes.big,
		label_color = Colors.Highways.strong_label,
	},
	
	Zoom(20) {
		thickness = 5,
		label_size = FontSizes.medium,
      	shield_scale_factor = ShieldScaleFactors.medium,
	},

	Zoom(40) {
		thickness = 4,
		color = Colors.Highways.medium_fill,
		border_color = Colors.Highways.medium_stroke,		
	},

	Zoom(60){
		border_width = 0,	
	},

	Zoom(80) {
		thickness = 3,		
		label_size = FontSizes.small,	
		label_color = Colors.Secondary.medium_label,		
	},


	Zoom(150) {	
		thickness = 2,	
		color = Colors.Highways.light_fill,
	},

	Zoom(250) {
		thickness = 2,	
	},

	Zoom(300){
		label_visibility = LabelVisibility.shield_only,
      	shield_scale_factor = ShieldScaleFactors.small,
	},
	
	LastZoom(320) {
		thickness = 0,

	}
}

Category("Streets") {
	MinZoom {
		border_width = 1,
		thickness = 15,
		color = Colors.Street.strong_fill,
		border_color = Colors.Street.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Street.strong_label,
		
	},
	
	Zoom(20) {
		border_width = 0.5,	
		thickness = 5.5,
		label_size = FontSizes.medium,		
		color = Colors.Street.medium_fill,
	},
	
	Zoom(40) {
		label_color = Colors.Street.medium_label,	
		border_width = 0,
		thickness = 3,
	},

	Zoom(60) {
		thickness = 2,	
		label_size = FontSizes.small,	
		label_color = Colors.Secondary.medium_label,		
	},	
	
	LastZoom(80) {
		thickness = 0,
		label_visibility = LabelVisibility.none,
	},
	
}

Category("Private") {
	MinZoom {
		thickness = 8,
		border_width = 1,
		color = Colors.Private.strong_fill,
		border_color = Colors.Private.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Private.strong_label,
	},

	Zoom(20) {
		thickness = 4,
		border_width = 0,		
		color = Colors.Private.medium_fill,
		label_size = FontSizes.medium,
	},


	LastZoom(55) {
		thickness = 0,
		label_visibility = LabelVisibility.none,
	}
}

Category("4X4 Trails") {
	MinZoom {
		thickness = 7,
		border_width = 1,
		color = Colors.Trails4X4.strong_fill,
		border_color = Colors.Trails4X4.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Trails4X4.strong_label,
	},

	Zoom(20) {
		thickness = 2,
		border_width = 0,	
		color = Colors.Trails4X4.medium_fill,
		border_color = Colors.Trails4X4.medium_stroke,
		label_size = FontSizes.medium,
	},
	
	LastZoom(55) {
		thickness = 0,
		label_visibility = LabelVisibility.none,
	}
}


-- Ramps and Exits -------------------------------------

Category("Ramps") {
	MinZoom {
		thickness = 14, 
		color = Colors.Ramps.strong_fill,
		border_width=2,
		border_color = Colors.Ramps.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Ramps.strong_label,

	},

	Zoom(20) {
		thickness = 5,
		border_width=1,
		label_size = FontSizes.medium,		
	},

	Zoom(40){
		color = Colors.Ramps.medium_fill,
		border_color = Colors.Ramps.medium_stroke,		
	},

	Zoom(80) {
		label_size = FontSizes.small,	
		label_color = Colors.Secondary.medium_label,		
	},	

	Zoom(90) {
		thickness = 3,
	},

	Zoom(150){
		color = Colors.Ramps.light_fill,	
		border_width=0,	
	},
	
	LastZoom(240) {
		thickness = 0,
		label_visibility = LabelVisibility.none,
	}
}

CategorySameAs("Exit","Ramps")

-- Parking -------------------------------------

Category("Parking") {
	MinZoom {
		thickness = 6,
		border_width = 1,
		color = Colors.Parking.strong_fill,
		border_color = Colors.Parking.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Parking.strong_label,
	},

	Zoom(20) {
		color = Colors.Parking.medium_fill,
		border_width = 0,		
		thickness = 2.5,
		label_size = FontSizes.medium,		
	},
	
	LastZoom(55) {
		thickness = 0,
		label_visibility = LabelVisibility.none,
	}
}

-- No-Auto -------------------------------------

Category("Railroads") {
	MinZoom {
		thickness = 10,
		color = Colors.Railroads.strong_stroke,
		texture = "rail_pattern",
	},

	Zoom(40) {
		color = Colors.Railroads.medium_stroke,		
	},

	Zoom(60) {
		thickness = 6,
		color = Colors.Railroads.light_stroke,		
	},
	
	LastZoom(80) {
		thickness = 1,
	}
}

Category("Ferry") {
	MinZoom {
		thickness = 5,
		color = Colors.Ferry.strong_stroke,
		texture = "longdash_pattern",
	},
	
	LastZoom(2000) {
	}
}

-------------------------------------

Category("Pedestrian") {
	MinZoom {
		thickness = 7,
		border_width = 1,
		color = Colors.Pedestrian.strong_fill,
		border_color = Colors.Pedestrian.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Pedestrian.strong_label,
	},

	Zoom(20) {
		thickness = 4,
		color = Colors.Pedestrian.medium_fill,
		border_color = Colors.Pedestrian.medium_stroke,
		label_size = FontSizes.medium,		
	},
	
	LastZoom(35) {
		border_width = 0,	
		thickness = 0,
		label_visibility = LabelVisibility.none,
	}
}

Category("Trails") {
	MinZoom {
		thickness = 7,
		border_width = 1,
		color = Colors.Trails.strong_fill,
		border_color = Colors.Trails.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Trails.strong_label,
	},

	Zoom(20) {
		thickness = 4,
		color = Colors.Trails.medium_fill,
		border_color = Colors.Trails.medium_stroke,
		label_size = FontSizes.medium,		
	},
	
	LastZoom(35) {
		thickness = 0,
		label_visibility = LabelVisibility.none,
	}
}

Category("Walkway") {
	MinZoom {
		thickness = 7,
		border_width = 1,
		color = Colors.Walkway.strong_fill,
		border_color = Colors.Walkway.strong_stroke,

		label_size = FontSizes.big,
		label_color = Colors.Walkway.strong_label,
	},

	Zoom(20) {
		thickness = 4,
		color = Colors.Walkway.medium_fill,
		border_color = Colors.Walkway.medium_stroke,
		label_size = FontSizes.medium,		
	},
	
	LastZoom(35) {
		thickness = 0,
		label_visibility = LabelVisibility.none,
	}
}


-- Parking lots -------------------------------------

Category("ParkingLot") {
	MinZoom {
		color = Colors.ParkingLots.strong_fill,
		label_visibility = LabelVisibility.none,
	},

	Zoom(80){
		color = Colors.ParkingLots.light_fill,
	},

	LastZoom(400) {
	},
}

Category("ParkingLotPins") {
	MinZoom {
		color = Colors.ParkingLotsPins.strong_fill,
		label_visibility = LabelVisibility.none,
	},

	Zoom(80){
		color = Colors.ParkingLotsPins.light_fill,
	},

	LastZoom(400) {
	},
}

-- Areas -------------------------------------



Category("Cities") {
	MinZoom {
		color = Colors.Cities.strong_fill,
		label_color = Colors.Cities.light_label,
		label_bgcolor = Colors.General.labels_bgcolor,
		label_size = FontSizes.huge,
		label_uppercase = true,
		label_bold = true,
	},

	Zoom(60) {
		label_size = FontSizes.big,
	},

	Zoom(120) {
		label_bold = true,
		label_color = Colors.Cities.strong_label,
		label_size = FontSizes.medium,
	},

	Zoom(180) {
		label_size = FontSizes.big,	
	},

	Zoom(300) {
		label_size = FontSizes.medium,	
	},


	LastZoom(9000) {
	}
}

Category("Stations") {
	MinZoom {
		color = Colors.Stations.strong_fill,
		label_color = Colors.Stations.strong_label,
		label_bgcolor = Colors.General.labels_bgcolor,
		label_size = FontSizes.medium,
	},

	Zoom(20) {
		label_size = FontSizes.medium,
	},	

	Zoom(80) {
		color = Colors.Stations.light_fill,	
		label_size = FontSizes.small,
	},	
	
	LastZoom(400) {
		label_visibility = LabelVisibility.none,
	}
}

Category("Islands"){
	MinZoom {
		color = Colors.General.map_background,
	}
}

Category("Parks") {
	MinZoom {
		color = Colors.Parks.strong_fill,
		label_color = Colors.Parks.strong_label,
		label_bgcolor = Colors.General.labels_bgcolor,
		label_size = FontSizes.medium,
	},
	
	Zoom(20) {
		label_size = FontSizes.medium,
	},
	
	Zoom(80) {
		label_size = FontSizes.small,
	},		

	Zoom(150){
		color = Colors.Parks.light_fill,
	},

	Zoom(200) {
		label_visibility = LabelVisibility.none,
	},		
	
	LastZoom(9000) {
	}
}

Category("Rivers") {
	MinZoom {
		color = Colors.Rivers.strong_fill,
		label_color = Colors.Rivers.strong_label,
		label_bgcolor = Colors.General.labels_bgcolor,
		label_size = FontSizes.medium,
	},

	Zoom(20) {
		label_size = FontSizes.medium,
	},	
	
	Zoom(80) {
		label_size = FontSizes.small,
	},

	Zoom(200) {
		label_size = FontSizes.tiny,
	},	
	
	LastZoom(9000) {
		label_visibility = LabelVisibility.none,
	}
}

Category("Lakes") {
	MinZoom {
		color = Colors.Lakes.strong_fill,
		label_color = Colors.Lakes.strong_label,
		label_bgcolor = Colors.General.labels_bgcolor,
		label_size = FontSizes.big,
	},

	Zoom(20) {
		label_size = FontSizes.medium,
	},	

	Zoom(80) {
		label_size = FontSizes.small,
	},	
	
	LastZoom(9000) {
		label_visibility = LabelVisibility.none,
	}
}

Category("Sea") {
	MinZoom {
		color = Colors.Lakes.strong_fill,
		label_color = Colors.Lakes.strong_label,
		label_bgcolor = Colors.General.labels_bgcolor,
		label_size = FontSizes.big,
	},

	Zoom(20) {
		label_size = FontSizes.medium,
	},

	Zoom(80) {
		label_size = FontSizes.small,
	},

	Zoom(1000) {
		label_size = FontSizes.medium,
	},	

	LastZoom(9000) {
		label_visibility = LabelVisibility.none,
	}
}

-- Other -------------------------------------

Category("Navigation") {
	MinZoom {
		thickness = 12,
		border_width = 2,
		color = Colors.Navigation.fill,
		border_color = Colors.Navigation.stroke,
		label_color = Colors.Navigation.label,
		label_bgcolor = Colors.Navigation.labelBg,
	},

	Zoom(20) {
		thickness = 8,
		border_width = 1,
	},
	
	LastZoom(9000) {
		thickness = 6,
	}
}

Category("Stop Point") {
	MinZoom {
		thickness = 12,
		border_width = 2,
		color = Colors.StopPoint.fill,
		border_color = Colors.StopPoint.stroke,
		label_color = Colors.StopPoint.label,
		label_bgcolor = Colors.StopPoint.labelBg,	
	},

	Zoom(20) {
		thickness = 8,
		border_width = 1,
	},
	
	LastZoom(9000) {
		thickness = 6,
	}
}

Category("Shared Navigation") {
	MinZoom {
		thickness = 12,
		border_width = 2,
		color = Colors.Shared.fill,
		border_color = Colors.Shared.stroke,
		label_color = Colors.Shared.label,
		label_bgcolor = Colors.Shared.labelBg,	
	},

	Zoom(20) {
		thickness = 8,
		border_width = 1,
	},
	
	LastZoom(9000) {
		thickness = 6,
	}
}

Category("Shared Stop") {
	MinZoom {
		thickness = 12,
		border_width = 2,
		color = Colors.SharedStop.fill,
		border_color = Colors.SharedStop.stroke,
		label_color = Colors.SharedStop.label,
		label_bgcolor = Colors.SharedStop.labelBg,	
	},

	Zoom(20) {
		thickness = 8,
		border_width = 1,
	},
	
	LastZoom(9000) {
		thickness = 6,
	}
}

Category("Route Snail") {
   MinZoom {
      thickness = 12,
      border_width = 2,
      color = Colors.RouteSnail.fill,
      border_color = Colors.RouteSnail.stroke,
      label_color = Colors.RouteSnail.label,
      label_bgcolor = Colors.RouteSnail.labelBg,
   },
   
   Zoom(20) {
      thickness = 8,
      border_width = 1,
   },
   
   LastZoom(9000) {
      thickness = 6,
   }
}

Category("Via Snail") {
   MinZoom {
      thickness = 12,
      border_width = 2,
      color = Colors.ViaSnail.fill,
      border_color = Colors.ViaSnail.stroke,
      label_color = Colors.ViaSnail.label,
      label_bgcolor = Colors.ViaSnail.labelBg,
   },
   
   Zoom(20) {
      thickness = 8,
      border_width = 1,
   },
   
   LastZoom(9000) {
      thickness = 6,
   }
}

Category("Shared Snail") {
   MinZoom {
      thickness = 12,
      border_width = 2,
      color = Colors.SharedSnail.fill,
      border_color = Colors.SharedSnail.stroke,
      label_color = Colors.SharedSnail.label,
      label_bgcolor = Colors.SharedSnail.labelBg,
   },
   
   Zoom(20) {
      thickness = 8,
      border_width = 1,
   },
   
   LastZoom(9000) {
      thickness = 6,
   }
}

Category("Shared Via Snail") {
   MinZoom {
      thickness = 12,
      border_width = 2,
      color = Colors.SharedViaSnail.fill,
      border_color = Colors.SharedViaSnail.stroke,
      label_color = Colors.SharedViaSnail.label,
      label_bgcolor = Colors.SharedViaSnail.labelBg,	
   },
   
   Zoom(20) {
      thickness = 8,
      border_width = 1,
   },
   
   LastZoom(9000) {
      thickness = 6,
   }
}

Category("Recording") {
   MinZoom {
      thickness = 12,
      border_width = 2,
      color = Colors.Recording.fill,
      border_color = Colors.Recording.stroke,
   },
   
   LastZoom(9000) {
      thickness = 12,
   }
}

Category("Recorded") {
   MinZoom {
      thickness = 12,
      border_width = 2,
      color = Colors.Recorded.fill,
      border_color = Colors.Recorded.stroke,
   },
   
   LastZoom(9000) {
      thickness = 12,
   }
}

