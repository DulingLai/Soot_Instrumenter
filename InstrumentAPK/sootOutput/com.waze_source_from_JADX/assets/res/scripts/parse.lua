-- Interface to native code. Works as series of reverse callback into native code with parsed parameters

local numGeneralValuesParsed = 0
local numCatZoomValuesParsed = 0

function getNumParsed()
	return numGeneralValuesParsed,numCatZoomValuesParsed;
end

--Stub, will be implemented in native code
local function generalValueCallbackDummy(key, value)
	-- print("General: "..key.." = ",value)
end

local function categoryZoomCallbackDummy(categoryName, lineOrArea, class, zoom, key, value)
	-- print("Cat: "..categoryName.."\t "..lineOrArea.."\t"..class.."\t zoom: "..tostring(zoom).."\t "..key.." = \t"..tostring(value))
end

-- Use local dummy implementation for testing/running as standalone script
local generalValueCallback = generalValueCallbackNative or generalValueCallbackDummy
local categoryZoomCallback = categoryZoomCallbackNative or categoryZoomCallbackDummy

-- Parses and sends to name code General section values
local function parseGeneral()
	for k,v in pairs(_general) do
		if not isPrivateField(k) then
			numGeneralValuesParsed = numGeneralValuesParsed + 1
			generalValueCallback(k,v)
		end
	end
end

-- Parses and sends to name code General specific values at given category/zoom
local function parseZoom(categoryName,lineOrArea, class, zoom)
	local zoomValue = zoom._zoomValue
	
	for k,v in pairs(zoom) do
		if not isPrivateField(k) then
			numCatZoomValuesParsed = numCatZoomValuesParsed + 1
			categoryZoomCallback(categoryName, lineOrArea, class, zoomValue, k, v)
		end
	end
end

-- Parse single category
local function parseCategory(categoryName,category)
	for i,v in ipairs(category) do
		parseZoom(categoryName, category._line_or_area, category._class, v)
	end
end

-- Parse category container
local function parseCategories()
	for k,v in pairs(_categories) do
		if not isPrivateField(k) then
			parseCategory(k,v)
		end
	end
end


-- Execute parse

parseGeneral()
parseCategories()