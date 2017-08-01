-- Global containters where all schema data is stored
_categories = {_type = "categories"}
_general = {_type = "general"}
_defaults = {_type = "defaults"}

-- Returns constructor for categories section 
function Category(name)
	return function (category)
		category._type = "category"
		_categories[name] = category
	end
end

-- Copies category contents from category srcName
function CategorySameAs(name, srcName)
	_categories[name] = _categories[srcName]
end

-- Returns constructor for zoom section
function Zoom(zoomValue)
	return function (zoom)
		zoom._type = "zoom"
		zoom._zoomValue = zoomValue
		return zoom
	end
end

-- Returns constructor for LastZoom section (declutter value)
function LastZoom(zoomValue)
	local maxZoom = 8192
	return function (zoom)
		zoom._type = "zoom"
		zoom._zoomValue = zoomValue or maxZoom
		zoom.last_zoom = true
		return zoom
	end
end

-- Constructor for MinZoom section (section where zoom set to 0)
function MinZoom(zoom)
	zoom._type = "zoom"
	zoom._zoomValue = 0
	return zoom		
end

-- Constructor for general section
function General(general)
	general._type = "general"
	_general = general
end

-- Constructor for defaults section
function MinZoomDefaults(defaults)
	_defaults._type = "defaults"
	_defaults = defaults
end
