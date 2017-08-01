------------
-- Public --
------------

-- Check if field name is private internal field (strarts with underscode)
function isPrivateField(fieldName)
  return fieldName:sub(1,1) == "_"
end

-- Initializes a set of values
function Set (list)
  local set = {}
  for _, l in ipairs(list) do set[l] = true end
  return set
end

-------------
-- Private --
-------------

-- Type of category
local CategoryType = {Any = 0, Line = 1, Area = 2 };
-- Index into individual zoomFields entries
local ZoomFieldIdx = {Type=1,DefaultValue=2, CategoryType=3}
-- Names for categories type
local CategoryTypeNames = { [CategoryType.Any] = "Any", [CategoryType.Line] = "Line", [CategoryType.Area] = "Area" }

local magenta = rgb(255,0,255)
local cyan = rgb(0,255,255)
local yellow = rgb(255,255,0)

-- Field types and default values
local zoomFields = {
	_type			= {"string",	""		, CategoryType.Any	}, -- Internal field
	_zoomValue		= {"number",	0		, CategoryType.Any	},
	
	color 			= {"number",	magenta	, CategoryType.Any	}, -- Public fields
	border_color 	= {"number",	cyan	, CategoryType.Line	},
	border_width 	= {"number", 	1		, CategoryType.Line	},
	thickness 		= {"number",	3		, CategoryType.Line	},
	texture			= {"string",    nil		, CategoryType.Line	},
	label_visibility = {"number",	LabelVisibility.all, CategoryType.Any	},
	label_size 		= {"number",	16		, CategoryType.Any	},
	label_bold 		= {"boolean",	false	, CategoryType.Any	},
	label_color 	= {"number",	yellow	, CategoryType.Any	},
	label_bgcolor 	= {"number",	cyan	, CategoryType.Any	},
	label_uppercase = {"boolean",	false 	, CategoryType.Any	},
	last_zoom		= {"boolean",	nil		, CategoryType.Any  },
    shield_scale_factor = {"number", 	1	, CategoryType.Line	}
}

-- Returns true if checkCategory field is allowed were targetCategory is required
local function isCategoryTypeMatching(checkCategory, targetCategory)
	if targetCategory == CategoryType.Any or checkCategory == CategoryType.Any then
		return true
	else
		return checkCategory == targetCategory
	end
end

-- Validate values in zoom section
local function validateZoomValues(categoryName, categoryType, zoom, zoomValue)
	if zoomValue == 0 then
		zoomValue = "MinZoom" --User-visible zoom value
	end
	
	for k,v in pairs(zoom) do
		
		-- Check if field is valid at all
		validate (zoomFields[k], 
			"Invalid field \""..k.."\" in category '"..categoryName.."' zoom '"..tostring(zoomValue).."'")
		
		-- Check if field value is of correct type
		validate (type(v) == zoomFields[k][ZoomFieldIdx.Type], 
			"Invalid type '" .. type(v) .. "' (should be '"..zoomFields[k][ZoomFieldIdx.Type].."') of field '"..k.."' in category '"..categoryName.."' zoom '"..tostring(zoomValue).."'")
		
		-- Check if field is compatible with category type (area/line)
		validate (isCategoryTypeMatching(categoryType, zoomFields[k][ZoomFieldIdx.CategoryType]), 
			"Field '"..k.."' is incompatible with '"..CategoryTypeNames[categoryType].."' type category '"..categoryName.."' zoom '"..tostring(zoomValue).."'")
	end
	
end

-- Validate signle zoom section
local function validateZoom(categoryName, categoryType, zoom)
	
	-- Check if valid zoom structure
	validate(zoom._type and zoom._type == "zoom", 
		"Not a zoom section in category #"..categoryName)
	
	-- Check if zoom value is of correct type
	validate(type(zoom._zoomValue) == "number", 
		"Invalid zoom value (\"" .. tostring(zoom._zoomValue) .. "\") in category #"..categoryName)

	-- Check individual zoom fields
	validateZoomValues(categoryName, categoryType, zoom, zoom._zoomValue)
	
	-- Set default values for unset fields in minZoom
	if zoom._zoomValue == 0 then
		for k,v in pairs(zoomFields) do
			-- Only set default values for parameters relevant to given category
			if isCategoryTypeMatching(categoryType, zoomFields[k][ZoomFieldIdx.CategoryType]) then
				if zoom[k] == nil then
					zoom[k] = _defaults[k] --First choice: user-defined defaults
				end
			
				if zoom[k] == nil then
					zoom[k] = zoomFields[k][ZoomFieldIdx.DefaultValue] --Second choice: hardcoded defaults fallback
				end
			end
		end
	end	
end


local ClassType = {Road = "road", Feature = "feature", Area = "area" };

-- List of valid line categories
local lineCategories = {
	["Freeways"]	= ClassType.Road,	
	["Primary"]		= ClassType.Road,		
	["Secondary"]	= ClassType.Road,	
	["Ramps"]		= ClassType.Road,	
	["Highways"]	= ClassType.Road,	
	["Exit"]		= ClassType.Road,
	["Streets"]		= ClassType.Road,   
	["Pedestrian"]	= ClassType.Feature,	
	["4X4 Trails"]	= ClassType.Road,	
	["Trails"]		= ClassType.Road,	
	["Walkway"]		= ClassType.Feature,	
	["Railroads"]	= ClassType.Feature,
	["Runways"]		= ClassType.Road,	
	["Ferry"]		= ClassType.Road,		
	["Private"]		= ClassType.Road,		
	["Parking"]		= ClassType.Road, 
	["Shore"]		= ClassType.Feature,	
	["Dirt"]		= ClassType.Road,
	["MinorFreeways"]	= ClassType.Road,  
	["Navigation"]	= ClassType.Feature, 
	["Recording"]	= ClassType.Feature, 
	["Recorded"]	= ClassType.Feature, 
	["Stop Point"]	= ClassType.Feature, 
	["Shared Navigation"]	= ClassType.Feature, 
	["Shared Stop"]	= ClassType.Feature,
   ["Route Snail"]	= ClassType.Feature,
   ["Via Snail"]	= ClassType.Feature,
   ["Shared Snail"]	= ClassType.Feature,
   ["Shared Via Snail"]	= ClassType.Feature
}

-- List of valid area categories
local areaCategories = {
	["Parks"]		= ClassType.Area, 
	["Hospitals"]	= ClassType.Area, 
	["Islands"]    = ClassType.Area,
	["Stations"]	= ClassType.Area, 
	["Cities"]		= ClassType.Area, 
	["AreaTBD12"]	= ClassType.Area,	
	["Rivers"]		= ClassType.Feature, 
	["Lakes"]		= ClassType.Area, 
	["Sea"]			= ClassType.Area,
	["ParkingLot"]	= ClassType.Area,
	["ParkingLotPins"]=ClassType.Area,
}

-- Validate single category section
local function validateCategory(name, category)
	-- Check if category name is valid
	validate(name and #name, 
		"Category name is empty")
	
	-- Check if category type is present
	validate(category._type, "Invalid category #"..tostring(name))
	
	-- ... and of correct value
	validate(category._type == "category", "Invalid type in category #"..tostring(name))
	
	-- 1. Not sure it is a good idea to update structure in validation function
	-- 2. Maybe line and area categories should be entire separate categories in schema	
	local categoryType = CategoryType.Any
	if lineCategories[name] then
		category._line_or_area = "line"
		category._class = lineCategories[name]
		categoryType = CategoryType.Line
	elseif areaCategories[name] then
		category._line_or_area = "area"
		category._class = areaCategories[name]
		categoryType = CategoryType.Area
	else
		error("Caterogy #" .. name .. " is nither valid line nor area category")
	end	
	
	hasMinZoom = false
	-- Check category contents
	for k,v in pairs(category) do
		-- Check valid entries (either zoom section or private field with metadata)
		if (type(k) == "number" and type(v) == "table") or (type(k) == "string" and isPrivateField(k)) then
			if (type(k) == "number") then
				validateZoom(name,categoryType,v)
				hasMinZoom = hasMinZoom or (v._zoomValue == 0)
			end
		else
			validate(false, "Not a valid element #" .. tostring(k) .. "in category #" .. name)
		end 
	end
	
	-- Check if MinZoom category present
	validate(hasMinZoom, 
		"Category #" .. name .. " does not have a MinZoom section")
end

-- Validate categories structure
local function validateCategories()
	
	-- Chheck if categories containter exist
	validate (_categories, 
		"Categories is nil")
		
	-- ... and not empty
	validate (#_categories == 0, 
		"Categories is empty" )
	
	for k,v in pairs(_categories) do
		if not isPrivateField(k) then
			validateCategory(k,v)
		end
	end
end

-- Validate default values section, wich technically similar to zoom section of category
local function validateDefaults()
	validateZoomValues("MinZoomDefaults", CategoryType.Any, _defaults, "default_min_zoom")
end

-- Execute the validation

validateDefaults()
validateCategories()
