-- Remove all potentially dangerous libraries
package = nil
os = nil
io = nil
coroutine = nil
debug = nil
--table = nil
--string = nil

-- Remote all pitentially dangerous functions
load = nil
loadstring = nil
loadfile = nil
dofile = nil
require = nil

------------
-- Public --
------------

--For debug purposes: prents nested tables structure
function printTable(t)
    function printTableHelper(t, spacing)
        for k,v in pairs(t) do
            print(spacing..tostring(k), v)
            if (type(v) == "table") then 
                printTableHelper(v, spacing.."\t")
            end
        end
    end

    printTableHelper(t, "");
end

-- Label Visibility
LabelVisibility = {
   all	       = 0xFFFF,
   shield_only  = 0x1,
   none         = 0x0
}


-- Log levels (matching those of Waze client)
LogLevel = {
	Debug 	= 1,
	Info  	= 2,
	Warning = 3,
	Error	= 4,
	Fatal	= 5,
	Crash	= 6
}

-- Log level names (for user-visible strings)
local LogLevelNames = {
	[LogLevel.Debug] 	= "DEBUG",
	[LogLevel.Info] 	= "INFO",
	[LogLevel.Warning]	= "WARNING",
	[LogLevel.Error] 	= "ERROR",
	[LogLevel.Fatal] 	= "FATAL",
	[LogLevel.Crash] 	= "CRASH",
}

-- Dummy, replace with native implementation
local function logDummy(level, string)
	print (LogLevelNames[level] .. ": " .. string)
end

local log_impl = logNative or logDummy

-- Logs text into native code with specified level
function log(level, ...)
	log_impl(level, table.concat{...} )
end	

-- Same as assert but logs message into native code
function validate(condition, message)
	if not condition then
		local errorStr = "SCHEMA PARSING ERRROR: " .. message
		-- log(LogLevel.Error, errorStr)
		error(errorStr)
	end
end


-- Convinience constructor for color values, to avoid ambiguity in component order and perform some additional checks 
-- Possible uses: 
--    rgb(255,0,255) 	-- 3 numerical values
--    rgb(0xff,0,0xff)  -- 3 hexanumerical values
-- 	  rgb(0xff00ff)		-- single hexanumerical value 0xRRGGBB
--
-- Returns plain 24-bit 0xAABBGGRR value with alpha set to 0xFF (fully opaque)
function rgb(...)
	local args = {...}
	
	validate(#args == 1 or #args == 3, "rgb() arguments should either be 3 separate numerical values or a single hex value")
	
	for i,v in ipairs(args) do
		validate(type(v) == "number", "rgb() argument #" .. tonumber(i) .. " is not a number")
	end	
	
	if #args == 1 then
		local val = args[1]
		validate(val >= 0 and val <=0xffffff, "rbg() argument invalid value. Should be in 0x000000-0xFFFFFF range. Got:" .. string.format("0x%X",val))
		
		local r,g,b = (val & 0xff0000)>>16, (val & 0x00ff00)>>8, (val & 0x0000ff)
		
		return 0xff << 24 | b << 16 | g << 8 | r 
	else
		local r,g,b = args[1],args[2],args[3]
		
		local function in_range(val) 
			return val >= 0 and val <=255
		end
		
		validate(in_range(r) and in_range(g) and in_range(b), "rgb() numerical values should be in range 0-255 (0x00-0xFF). Got: " 
			.. tostring(r) .. "," .. tostring(g) .. "," .. tostring(b))
			
		return 0xff << 24 | b << 16 | g << 8 | r
	end
end

-- Convinience constructor for color values, to avoid ambiguity in component order and perform some additional checks 
-- Possible uses: 
--    rgba(255,0,255,200) 		-- 4 numerical values
--    rgba(0xff,0,0xff,0xc8)  	-- 4 hexanumerical values
-- 	  rgba(0xff00ffc8)			-- single hexanumerical value 0xRRGGBBAA	
--
-- Returns plain 24-bit 0xAABBGGRR value
function rgba(...)
	local args = {...}
	
	validate(#args == 1 or #args == 4, "rgba() arguments should either be 4 separate numerical values or a single hex value")
	
	for i,v in ipairs(args) do
		validate(type(v) == "number", "rgba() argument #" .. tonumber(i) .. " is not a number")
	end	
	
	if #args == 1 then
		local val = args[1]
		validate(val >= 0 and val <=0xffffffff, "rbga() argument invalid value. Should be in 0x00000000-0xFFFFFFFF range. Got:" .. string.format("0x%X",val))
		
		local r,g,b,a = (val & 0xff000000)>>24, (val & 0x00ff0000)>>16, (val & 0x0000ff00)>>8, (val & 0x000000ff)
		
		return a << 24 | b << 16 | g << 8 | r 
	else
		local r,g,b,a = args[1],args[2],args[3],args[4]
		
		local function in_range(val) 
			return val >= 0 and val <=255
		end
		
		validate(in_range(r) and in_range(g) and in_range(b) and in_range(a), "rgba() numerical values should be in range 0-255 (0x00-0xFF). Got: " 
			.. tostring(r) .. "," .. tostring(g) .. "," .. tostring(b) .. "," .. tostring(a))
			
		return a << 24 | b << 16 | g << 8 | r
	end
end


local function round(n) return math.floor(n+0.5) end

--[[
-- Converts HSV to RGB (input and output range: 0 - 255)
function hsvToRgb(h, s, v)
   if s == 0 then return v,v,v end
   h, s, v = h/255*6, s/255, v/255
   local c = v*s
   local x = (1-math.abs(h%2-1))*c
   local m,r,g,b = v-c, 0,0,0
   if h < 1     then r,g,b = c,x,0
   elseif h < 2 then r,g,b = x,c,0
   elseif h < 3 then r,g,b = 0,c,x
   elseif h < 4 then r,g,b = 0,x,c
   elseif h < 5 then r,g,b = x,0,c
   else              r,g,b = c,0,x
   end
   return round(r+m)*255),round((g+m)*255),round((b+m)*255)
end
]]


local function clamp1(val)
	return math.max(0,math.min(1,val))
end

-- gloval library for color manipulations
color = {}

-- Converts HSV to RGB (input range: 0 - 1,output range: 0 - 255), 'a' transparently passed through and can be ignored
function color.hsvToRgb(h, s, v, a)
   if s == 0 then return round(v*255),round(v*255),round(v*255),a end
   h = h*6
   local c = v*s
   local x = (1-math.abs(h%2-1))*c
   local m,r,g,b = v-c, 0,0,0
   if h < 1     then r,g,b = c,x,0
   elseif h < 2 then r,g,b = x,c,0
   elseif h < 3 then r,g,b = 0,c,x
   elseif h < 4 then r,g,b = 0,x,c
   elseif h < 5 then r,g,b = x,0,c
   else              r,g,b = c,0,x
   end
   return round((r+m)*255),round((g+m)*255),round((b+m)*255),a
end

-- Converts RGB to HSV (input range: 0-255,output range: 0 - 1), 'a' transparently passed through and can be ignored
function color.rgbToHsv (r,g,b,a)
   r,g,b = r/255,g/255,b/255
   local cmax,cmin = math.max(r,g,b), math.min(r,g,b)
   local d,h,s = cmax-cmin, 0, 0
   if d == 0 then h = 0
   elseif cmax == r then h = 1/6*((g-b)/d%6)
   elseif cmax == g then h = 1/6*((b-r)/d+2)
   elseif cmax == b then h = 1/6*((r-g)/d+4)
   end

   if cmax > 0 then s = d/cmax
   else s = 0
   end

   return h,s,cmax,a
end

--Decomposes encoded rgba value into 4 separate components
function color.rgbDecomp(rgbaCol)
	return (rgbaCol & 0x000000ff), (rgbaCol & 0x0000ff00)>>8, (rgbaCol & 0x00ff0000)>>16, (rgbaCol & 0xff000000)>>24
end

--Composes agbr value from 4 separate components 
function color.rgbComp(r,g,b,a)
	return a << 24 | b << 16 | g << 8 | r
end

--Lighten color by given amount in range 0 - 1.0 
function color.lighten(amount,rgbCol)
	local h,s,v,a = color.rgbToHsv(color.rgbDecomp(rgbCol))
	return color.rgbComp(color.hsvToRgb(h,s,clamp1(v+amount),a))
end

--Darken color by given amount in range 0 - 1.0 
function color.darken(amount,rgbCol)
	local h,s,v,a = color.rgbToHsv(color.rgbDecomp(rgbCol))
	return color.rgbComp(color.hsvToRgb(h,s,clamp1(v-amount),a))
end

--Increase color saturation by given amount in range 0 - 1.0 
function color.saturate(amount,rgbCol)
	local h,s,v,a = color.rgbToHsv(color.rgbDecomp(rgbCol))
	return color.rgbComp(color.hsvToRgb(h,clamp1(s+amount),v,a))
end

--Decrease color saturation by given amount in range 0 - 1.0 
function color.desaturate(amount,rgbCol)
	local h,s,v,a = color.rgbToHsv(color.rgbDecomp(rgbCol))
	return color.rgbComp(color.hsvToRgb(h,clamp1(s-amount),v,a))
end

--Shift color hue given amount in range -1.0 - 1.0. 
--Value of 1 (or -1) is a full color circle rotation (no, change, samme as 0). 0.5 - opposite color  
function color.shiftHue(amount,rgbCol)
	local h,s,v,a = color.rgbToHsv(color.rgbDecomp(rgbCol))
	return color.rgbComp(color.hsvToRgb((h+amount)%1.0,s,v,a))
end

--Change color hue to opposite, same as color.shiftHue(0.5,color)
function color.invertHue(rgbCol)
	local h,s,v,a = color.rgbToHsv(color.rgbDecomp(rgbCol))
	return color.rgbComp(color.hsvToRgb((h+0.5)%1.0,s,v,a))
end

--Fully invert color, leaving alpha intact
function color.invert(rgbCol)
	local r,g,b,a = color.rgbDecomp(rgbCol)
	return color.rgbComp(255-r,255-g,255-b,a)
end
