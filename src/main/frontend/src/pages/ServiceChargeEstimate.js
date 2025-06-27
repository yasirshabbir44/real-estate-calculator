import React, { useState } from 'react';

function ServiceChargeEstimate() {
  const [propertyType, setPropertyType] = useState('apartment');
  const [propertySize, setPropertySize] = useState(1000);
  const [propertyAge, setPropertyAge] = useState(0);
  const [amenities, setAmenities] = useState({
    pool: false,
    gym: false,
    concierge: false,
    security: true,
    parking: true,
    playground: false
  });

  // Calculate estimated service charge
  const calculateEstimate = () => {
    // Base rates per square foot based on property type
    const baseRates = {
      apartment: 12,
      villa: 8,
      townhouse: 10
    };

    // Base calculation
    let estimate = propertySize * (baseRates[propertyType] / 1000);

    // Adjust for property age (newer properties might have higher charges)
    if (propertyAge < 2) {
      estimate *= 1.1; // 10% higher for new properties
    } else if (propertyAge > 10) {
      estimate *= 0.9; // 10% lower for older properties
    }

    // Add for amenities
    if (amenities.pool) estimate += 500;
    if (amenities.gym) estimate += 300;
    if (amenities.concierge) estimate += 800;
    if (amenities.security) estimate += 400;
    if (amenities.parking) estimate += 200;
    if (amenities.playground) estimate += 150;

    return estimate.toFixed(2);
  };

  const handleAmenityChange = (amenity) => {
    setAmenities({
      ...amenities,
      [amenity]: !amenities[amenity]
    });
  };

  const estimate = calculateEstimate();

  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">Service Charge Estimate</h1>
      <p className="mb-6">
        Estimate your annual service charges based on property details and amenities.
        This is an approximate calculation and actual charges may vary.
      </p>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-xl font-semibold mb-4">Property Details</h2>
          
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Property Type</label>
            <select 
              value={propertyType}
              onChange={(e) => setPropertyType(e.target.value)}
              className="w-full p-2 border border-gray-300 rounded"
            >
              <option value="apartment">Apartment</option>
              <option value="villa">Villa</option>
              <option value="townhouse">Townhouse</option>
            </select>
          </div>
          
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Property Size (sq ft): {propertySize}
            </label>
            <input 
              type="range" 
              min="500" 
              max="10000" 
              step="100"
              value={propertySize}
              onChange={(e) => setPropertySize(Number(e.target.value))}
              className="w-full"
            />
          </div>
          
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Property Age (years): {propertyAge}
            </label>
            <input 
              type="range" 
              min="0" 
              max="20" 
              value={propertyAge}
              onChange={(e) => setPropertyAge(Number(e.target.value))}
              className="w-full"
            />
          </div>
          
          <h3 className="text-lg font-medium mt-6 mb-3">Amenities</h3>
          <div className="space-y-2">
            {Object.keys(amenities).map((amenity) => (
              <div key={amenity} className="flex items-center">
                <input
                  type="checkbox"
                  id={amenity}
                  checked={amenities[amenity]}
                  onChange={() => handleAmenityChange(amenity)}
                  className="mr-2"
                />
                <label htmlFor={amenity} className="capitalize">
                  {amenity}
                </label>
              </div>
            ))}
          </div>
        </div>
        
        <div>
          <div className="bg-white p-6 rounded-lg shadow-md mb-6">
            <h2 className="text-xl font-semibold mb-4">Estimated Annual Service Charge</h2>
            <div className="text-4xl font-bold text-blue-600">
              AED {estimate}
            </div>
            <p className="text-sm text-gray-500 mt-2">
              Approximately AED {(estimate / 12).toFixed(2)} per month
            </p>
          </div>
          
          <div className="bg-yellow-50 p-4 rounded-lg">
            <h3 className="text-lg font-semibold text-yellow-800 mb-2">Important Note</h3>
            <p className="text-yellow-700">
              This is an estimate based on average service charges in Dubai. 
              Actual service charges may vary based on the specific building, 
              developer, and community. Contact the property management for exact figures.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ServiceChargeEstimate;