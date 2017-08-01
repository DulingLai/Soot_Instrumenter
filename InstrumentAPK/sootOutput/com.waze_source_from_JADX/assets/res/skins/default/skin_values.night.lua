-----------------------------
-----------------------------
--                         --
--      NIGHT SCHEMA       -- 
--      Apr 20 2017        -- 
--                         -- 
-----------------------------
-----------------------------

-----------------------
-- Params/Color File --
-----------------------
-- A set of parameters for structure file. Mainly colors, but might define many other
-- things. This file does not have any pre-defined structure, only parameters defined 
-- under names structure fuke expects them to be found.

-- Local 'palette' (constants) for convinience
local Palette = {

	base_default = rgb(0x0095FF),
	black = rgb(0x000000),
	white = rgb(0xFFFFFF),
	red = rgb(0xFF0000),

	map_background = rgb(0x555a60),
	map_missing = rgb(0x60666c),

	labels = rgb(0xCADADC),
	labels_strong = rgb(0xD2E2E4),
	labels_bgcolor = rgba(0x091421bf),

	oneWay = rgba(0xffffff44),
	points = rgb(0xb1bedc),
	selection = rgb(0x88c1e3),

	-- Roads ------------------------

	freeways = rgb(0x2b2e33),
	primary = rgb(0x272d34),
	secondary = rgb(0x30373c),
	highways = rgb(0x33363d),
	street = rgb(0x343b41),
	private = rgb(0x343F46),
	trails4x4 = rgb(0x2f3947),

	-- Ramps & Exit ------------------------

	ramps = rgb(0x262E36),

	-- Parking ----------------------------

	parking = rgb(0x343F46),
	parking_lots = rgb(0x464b52),
	parking_lots_pins = rgb(0x3B5987),

	-- No-Auto ------------------------

	railroads = rgb(0x8e97a4),
	ferry_stroke = rgb(0x6993b4),

	pedestrian = rgb(0x303a40),
	trails = rgb(0x313f48),
	walkway = rgb(0x202E3E),

	-- Areas ------------------------

	cities = rgb(0xF9F8F4),
	stations = rgb(0x464b52),
	parks = rgb(0x537e6d),
	sea = rgb(0x698699),
	lakes = rgb(0x536C7C),
	rivers = rgb(0x6e8594),

	label_station = rgb(0x939ca5),
	label_cities = rgb(0xB9C5D3),
	label_vegetation = rgb(0x9baca0),
	label_water = rgb(0x81BAD2),

	-- Others --------------------------

	navigation = rgb(0x07e4ff),
	stop_point = rgb(0xAE49E9),
	shared = rgb(0x2EB773),
	shared_stop = rgb(0x69FB97),
   	snail = rgb(0x0B7484),
   	snail_via = rgb(0x4A3955),
   	snail_via = rgb(0x246454),
   	snail_via_share = rgb(0x317043),
   	recording = rgb(0xFF0000),
   	recorded = rgb(0x0000FF),

	alt_color_1 = rgb(0x00CF8A),
	alt_color_2 = rgb(0x1DC4FF),
	alt_color_3 = rgb(0xC160EB),
	alt_color_current = rgb(0x9C86FF),

	traffic_route_light = rgb(0xFFCD62),
	traffic_route_moderate = rgb(0xFF9752),
	traffic_route_heavy = rgb(0xFF716F),
	traffic_route_standstill = rgb(0xD52D2A),	
	--traffic_route_standstill = 0,			
	traffic_route_closed_road = rgb(0xA42D2B),	

	traffic_arrow_light = rgb(0xff8e42),
	traffic_arrow_moderate = rgb(0xff5e01),
	traffic_arrow_heavy = rgb(0xed3030),
	traffic_arrow_standstill = rgb(0x9C1E1C),
	--traffic_arrow_standstill = 0,			
	traffic_arrow_closed_road = rgb(0x000000),	


}

-- General params --------------------------------

Params = {
	Defaults = {
		font_size = 12,
		declutter_max = 2147483647,
	},
		
	--Example
	Street = {
		font_size = 17,
		declutter = 120,
	}
}

-- Colors -------------------------------------

Colors = {
	General = {
		map_background = Palette.map_background,
		missing = Palette.map_missing,
		labels_bgcolor = Palette.labels_bgcolor,

		map_selection_color = Palette.selection,
		map_one_way_color = Palette.oneWay,
    	map_points_color = Palette.points,

		-- Alt colors: { ALT_COLOR_1, ALT_COLOR_2, ALT_COLOR_3, CURRENT_ROUTE_COLOR }
		nav_alt_colors = { Palette.alt_color_1, Palette.alt_color_2, Palette.alt_color_3, Palette.alt_color_current },

		-- Traffic types:{LIGHT, MODERATE, HEAVY, STAND_STILL, UNUSED, CLOSED_ROAD}
		traffic_route_colors = { Palette.traffic_route_light, Palette.traffic_route_moderate, Palette.traffic_route_heavy, Palette.traffic_route_standstill, rgb(0xE12D2D), Palette.traffic_route_closed_road };
		traffic_arrow_colors = { Palette.traffic_arrow_light, Palette.traffic_arrow_moderate, Palette.traffic_arrow_heavy, Palette.traffic_arrow_standstill, rgb(0xbb0b0b), Palette.traffic_arrow_closed_road },
	},

	Defaults = {
		fill = Palette.base_default,
		strokes = Palette.black,
		labels = Palette.labels,
		labels_bgcolor = Palette.labels_bgcolor,
	},
	
	-- Roads -------------------------------------

	Freeways = {
		light_stroke = color.lighten(0.1,Palette.freeways),
		light_fill = color.lighten(0.08,Palette.freeways),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.freeways,
		medium_fill = color.lighten(0.05,Palette.freeways),
		medium_label = Palette.labels,

		strong_stroke = color.darken(0.1,Palette.freeways),
		strong_fill = Palette.freeways,
		strong_label = Palette.labels_strong,
	},

	Primary = {
		light_stroke = color.lighten(0.15,Palette.primary),
		light_fill = color.lighten(0.06,Palette.primary),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.primary,
		medium_fill = color.lighten(0.04,Palette.primary),
		medium_label = Palette.labels,

		strong_stroke = color.darken(0.1,Palette.primary),
		strong_fill = Palette.primary,
		strong_label = Palette.labels_strong,
	},

	Secondary = {
		light_stroke = color.lighten(0.1,Palette.secondary),
		light_fill = color.desaturate(0.01,color.lighten(0.07,Palette.secondary)),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.secondary,
		medium_fill = color.lighten(0.05,Palette.secondary),
		medium_label = Palette.labels,

		strong_stroke = color.darken(0.1,Palette.secondary),
		strong_fill = Palette.secondary,
		strong_label = Palette.labels_strong,
	},

	Highways = {
		light_stroke = color.lighten(0.04,Palette.highways),
		light_fill = color.lighten(0.07,Palette.highways),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.highways,
		medium_fill = color.lighten(0.04,Palette.highways),
		medium_label = Palette.labels,

		strong_stroke = color.darken(0.1,Palette.highways),
		strong_fill = Palette.highways,
		strong_label = Palette.labels_strong,
	},

	Street = {
		light_stroke = color.darken(0.05,Palette.street),
		light_fill = color.lighten(0.1,Palette.street),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.street,
		medium_fill = color.lighten(0.05,Palette.street),
		medium_label = Palette.labels,

		strong_stroke = Palette.map_background,
		strong_fill = Palette.street,
		strong_label = Palette.labels_strong,
	},

	Private = {
		light_stroke = Palette.map_background,
		light_fill = color.lighten(0.2,Palette.private),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.map_background,
		medium_fill = color.shiftHue(-0.03,color.lighten(0.02,Palette.private)),
		medium_label = Palette.labels,

		strong_stroke = Palette.map_background,
		strong_fill = Palette.private,
		strong_label = Palette.labels_strong,
	},

	Trails4X4 = {
		light_stroke = color.darken(0.1,Palette.trails4x4),
		light_fill = color.lighten(0.2,Palette.trails4x4),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.trails4x4,
		medium_fill = color.lighten(0.05,Palette.trails4x4),
		medium_label = Palette.labels,

		strong_stroke = color.darken(0.1,Palette.trails4x4),
		strong_fill = Palette.trails4x4,
		strong_label = Palette.labels_strong,
	},

	--Ramps and Exits-------------------------------------

	Ramps = {
		light_stroke = Palette.rampsStroke,
		light_fill = Palette.ramps,

		medium_stroke = Palette.ramps,
		medium_fill = color.desaturate(0.1,color.lighten(0.05,Palette.ramps)),

		strong_stroke = color.shiftHue(0.05,color.darken(0.05,Palette.ramps)),
		strong_fill = Palette.ramps,
	},

	Exit = {
		light_stroke = Palette.rampsStroke,
		light_fill = Palette.ramps,

		medium_stroke = Palette.ramps,
		medium_fill = color.shiftHue(-0.2,color.lighten(0.15,Palette.ramps)),

		strong_stroke = color.shiftHue(0.2,color.darken(0.15,Palette.ramps)),
		strong_fill = Palette.ramps,
	},

	--Parking-------------------------------------

	Parking = {
		light_stroke = Palette.map_background,
		light_fill = color.lighten(0.1,Palette.parking),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.map_background,
		medium_fill = color.shiftHue(-0.03,color.lighten(0.02,Palette.parking)),
		medium_label = Palette.labels,

		strong_stroke = Palette.map_background,
		strong_fill = Palette.parking,
		strong_label = Palette.labels_strong,
	},

	ParkingLots = {
		light_fill = color.saturate(0.07,color.lighten(0.05,Palette.parking_lots)),
		strong_fill = Palette.parking_lots,		
	},


	ParkingLotsPins = {
		light_fill = color.saturate(0.07,color.lighten(0.05,Palette.parking_lots_pins)),
		strong_fill = Palette.parking_lots_pins,	
	},

	--No-Auto-------------------------------------


	Railroads = {
		light_stroke = color.lighten(0.13,Palette.railroads),
		light_fill = Palette.railroads,

		medium_stroke = color.lighten(0.07,Palette.railroads),
		medium_fill = Palette.railroads,

		strong_stroke = Palette.railroads,
		strong_fill = Palette.railroads,

		texture = "rail_pattern",
		-- shadow = Palette.base_default,
	},

	Ferry = {
		light_stroke = Palette.ferry_stroke,
		light_label = color.darken(0.05,Palette.label_water),

		medium_stroke = Palette.ferry_stroke,
		medium_label = color.darken(0.07,Palette.label_water),

		strong_stroke = Palette.ferry_stroke,
		strong_label = color.darken(0.1,Palette.label_water),

		texture = "longdash_pattern",
	},

	Pedestrian = {
		light_stroke = Palette.map_background,
		light_fill = color.lighten(0.07,Palette.pedestrian),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.map_background,
		medium_fill = color.lighten(0.05,Palette.pedestrian),
		medium_label = Palette.labels,

		strong_stroke = Palette.map_background,
		strong_fill = Palette.pedestrian,
		strong_label = Palette.labels_strong,
	},

	Trails = {
		light_stroke = Palette.map_background,
		light_fill = color.lighten(0.07,Palette.trails),
		light_label = color.darken(0.10,Palette.trails),

		medium_stroke = Palette.map_background,
		medium_fill = color.lighten(0.05,Palette.trails),
		medium_label = color.darken(0.15,Palette.trails),

		strong_stroke = Palette.map_background,
		strong_fill = Palette.trails,
		strong_label = color.darken(0.18,Palette.trails),
	},

	Walkway = {
		light_stroke = Palette.map_background,
		light_fill = color.lighten(0.07,Palette.walkway),
		light_label = color.lighten(0.1,Palette.labels),

		medium_stroke = Palette.map_background,
		medium_fill = color.lighten(0.05,Palette.walkway),
		medium_label = Palette.labels,

		strong_stroke = Palette.map_background,
		strong_fill = Palette.walkway,
		strong_label = Palette.labels_strong,
	},

	-- Areas -------------------------------------

	Cities = {
		light_fill = Palette.cities,
		light_label = color.saturate(0.01,color.darken(0.2,Palette.label_cities)),

		medium_label = color.lighten(0.25,Palette.label_cities),

		strong_fill = Palette.cities,
		strong_label = Palette.label_cities,
	},


	Stations = {
		light_fill = color.saturate(0.07,color.lighten(0.05,Palette.stations)),
		
		strong_fill = Palette.stations,
		strong_label = Palette.label_station,
	},

	Parks = {
		strong_fill = Palette.parks,
		strong_label = Palette.label_vegetation,	
	},

	Rivers = {
		strong_fill = Palette.rivers,
		strong_label = Palette.label_water,	
	},

	Lakes = {
		strong_fill = Palette.lakes,
		strong_label = Palette.label_water,	
	},

	Sea = {
		strong_fill = Palette.sea,
		strong_label = Palette.label_water,
	},

	-- Navigation -------------------------------------

	Navigation = {
		fill = Palette.navigation,
		stroke = color.darken(0.3,Palette.navigation),
		label = Palette.white,
		labelBg = color.shiftHue(0.015,color.darken(0.4,Palette.navigation)),
	},
	
	StopPoint = {
		fill = Palette.stop_point,
		stroke = color.darken(0.2,Palette.stop_point),
		label = Palette.white,
		labelBg = Palette.stop_point,
	},

	Shared = {
		fill = Palette.shared,
		stroke = color.darken(0.2,Palette.shared),
		label = Palette.white,
		labelBg = color.shiftHue(0.015,color.darken(0.2,Palette.shared)),
	},

	SharedStop = {
		fill = Palette.shared_stop,
		stroke = color.darken(0.2,Palette.shared_stop),
		label = Palette.white,
		labelBg = color.shiftHue(0.02,color.darken(0.3,Palette.shared_stop)),
	},
   
   RouteSnail = {
      fill = Palette.snail,
      stroke = color.darken(0.2,Palette.snail),
   },
   
   ViaSnail = {
      fill = Palette.snail_via,
      stroke = color.darken(0.2,Palette.snail_via),
   },
   
   SharedSnail = {
      fill = Palette.snail_via,
      stroke = color.darken(0.2,Palette.snail_via),
   },
   
   SharedViaSnail = {
      fill = Palette.snail_via_share,
      stroke = color.darken(0.2,Palette.snail_via_share),
   },
   
   Recording = {
      fill = Palette.recording,
      stroke = color.darken(0.2,Palette.recording),
   },
   
   Recorded = {
      fill = Palette.recorded,
      stroke = color.darken(0.2,Palette.recorded),
   },
}
