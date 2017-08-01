--For testing: runs schema as a standalone script

local require = require
local dofile = dofile

require "enviroment"
dofile "../skins/default/skin_values.day.lua"
require "structurelib"
dofile "../skins/default/skin_structure.main.lua"
require "validation"
require "parse"

local numGeneralValuesParsed, numCatZoomValuesParsed = getNumParsed()
print ("CHECK COMPLETE. Parsed:\n\t" .. numGeneralValuesParsed .. " general values\n\t" .. numCatZoomValuesParsed .. " values for category/zoom.")

--[[

local function test_rgb2hsv2rgb(r,g,b)
   local function t2s(a,b,c)
   		return string.format("%3d, %3d, %3d",a,b,c)
   end

   local function f2s(a,b,c)
   		return string.format("%.2f, %.2f, %.2f",a,b,c)
   end

	local h,s,v	= color.rgbToHsv(r,g,b)
	--local r1,g1,b1 = hsvToRgb(h,s,v)
	local col = color.rgbComp(r,g,b,0xff)
	
	-- local col2 = color.shiftHue(0.001,col)
	local col2 = color.darken(0.5,col)

	local r1,g1,b1 = color.rgbDecomp(col2)
	local h1,s1,v1 = color.rgbToHsv(r1,g1,b1) 
	local ok = "x"
	if (r==r1 and g==g1 and b==b1) then ok = "." end
	print (ok .. " " .. t2s(r,g,b).." -> \t"..f2s(h,s,v).." -> \t"..f2s(h1,s1,v1).." -> \t"..t2s(r1,g1,b1))
end 

test_rgb2hsv2rgb(0,0,0)
test_rgb2hsv2rgb(255,255,255)
test_rgb2hsv2rgb(255,0,0)
test_rgb2hsv2rgb(0,255,0)
test_rgb2hsv2rgb(0,0,255)
test_rgb2hsv2rgb(128,128,128)
test_rgb2hsv2rgb(254,254,254)
test_rgb2hsv2rgb(1,1,1)

for v = 1,100 do
  test_rgb2hsv2rgb(math.random(0,255),math.random(0,255),math.random(0,255))
end


]]


--[[

print("Categories:")
printTable(_categories)
print("\n\n")

print("General:")
printTable(_general)
print("\n\n")

print("Defaults:")
printTable(_defaults)
print("\n\n")



for i,v in pairs(_G) do
	-- if (type(v) == "function") then
		print(i, " -> ", v)
	-- end
end
]]
