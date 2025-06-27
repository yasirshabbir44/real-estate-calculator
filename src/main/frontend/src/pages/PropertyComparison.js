import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const PropertyComparison = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const queryParams = new URLSearchParams(location.search);
  const property1IdParam = queryParams.get('property1Id');
  const property2IdParam = queryParams.get('property2Id');

  const [properties, setProperties] = useState([]);
  const [selectedProperty1Id, setSelectedProperty1Id] = useState(property1IdParam || '');
  const [selectedProperty2Id, setSelectedProperty2Id] = useState(property2IdParam || '');
  const [property1, setProperty1] = useState(null);
  const [property2, setProperty2] = useState(null);
  const [comparison, setComparison] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [comparisonParams, setComparisonParams] = useState({
    propertyAppreciationRate: 5,
    holdingPeriodYears: 5
  });

  // Fetch all properties for dropdown selection
  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const response = await axios.get('/api/properties');
        setProperties(response.data);
      } catch (err) {
        console.error('Error fetching properties:', err);
        setError('Failed to load properties. Please try again later.');
      }
    };

    fetchProperties();
  }, []);

  // Fetch property details when IDs are selected
  useEffect(() => {
    const fetchPropertyDetails = async (propertyId, setPropertyFunction) => {
      if (!propertyId) return;
      
      try {
        const response = await axios.get(`/api/properties/${propertyId}`);
        setPropertyFunction(response.data);
      } catch (err) {
        console.error(`Error fetching property ${propertyId}:`, err);
        setError(`Failed to load property details for ID ${propertyId}.`);
      }
    };

    if (selectedProperty1Id) {
      fetchPropertyDetails(selectedProperty1Id, setProperty1);
    } else {
      setProperty1(null);
    }

    if (selectedProperty2Id) {
      fetchPropertyDetails(selectedProperty2Id, setProperty2);
    } else {
      setProperty2(null);
    }
  }, [selectedProperty1Id, selectedProperty2Id]);

  // Update URL when properties change
  useEffect(() => {
    const params = new URLSearchParams();
    if (selectedProperty1Id) params.append('property1Id', selectedProperty1Id);
    if (selectedProperty2Id) params.append('property2Id', selectedProperty2Id);
    
    navigate({
      pathname: location.pathname,
      search: params.toString()
    }, { replace: true });
  }, [selectedProperty1Id, selectedProperty2Id, navigate, location.pathname]);

  const handlePropertyChange = (e, setPropertyFunction) => {
    setPropertyFunction(e.target.value);
  };

  const handleParamChange = (e) => {
    const { name, value } = e.target;
    setComparisonParams({
      ...comparisonParams,
      [name]: parseFloat(value)
    });
  };

  const compareProperties = async () => {
    if (!selectedProperty1Id || !selectedProperty2Id) {
      setError('Please select two properties to compare.');
      return;
    }

    try {
      setLoading(true);
      setError(null);
      
      const response = await axios.post('/api/property-comparisons/compare-properties', null, {
        params: {
          property1Id: selectedProperty1Id,
          property2Id: selectedProperty2Id,
          propertyAppreciationRate: comparisonParams.propertyAppreciationRate,
          holdingPeriodYears: comparisonParams.holdingPeriodYears
        }
      });
      
      setComparison(response.data);
    } catch (err) {
      console.error('Error comparing properties:', err);
      setError('Failed to compare properties. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">Property Comparison</h1>
      
      {/* Property Selection */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold mb-4">Select Properties to Compare</h2>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
          <div>
            <label htmlFor="property1" className="form-label">Property 1</label>
            <select
              id="property1"
              value={selectedProperty1Id}
              onChange={(e) => handlePropertyChange(e, setSelectedProperty1Id)}
              className="form-input"
            >
              <option value="">Select a property</option>
              {properties.map(property => (
                <option key={`p1-${property.id}`} value={property.id}>
                  {property.name} - {property.location}
                </option>
              ))}
            </select>
          </div>
          
          <div>
            <label htmlFor="property2" className="form-label">Property 2</label>
            <select
              id="property2"
              value={selectedProperty2Id}
              onChange={(e) => handlePropertyChange(e, setSelectedProperty2Id)}
              className="form-input"
            >
              <option value="">Select a property</option>
              {properties.map(property => (
                <option key={`p2-${property.id}`} value={property.id}>
                  {property.name} - {property.location}
                </option>
              ))}
            </select>
          </div>
        </div>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
          <div>
            <label htmlFor="propertyAppreciationRate" className="form-label">
              Annual Property Appreciation Rate (%)
            </label>
            <input
              type="number"
              id="propertyAppreciationRate"
              name="propertyAppreciationRate"
              value={comparisonParams.propertyAppreciationRate}
              onChange={handleParamChange}
              className="form-input"
              min="0"
              max="20"
              step="0.1"
            />
          </div>
          
          <div>
            <label htmlFor="holdingPeriodYears" className="form-label">
              Holding Period (Years)
            </label>
            <input
              type="number"
              id="holdingPeriodYears"
              name="holdingPeriodYears"
              value={comparisonParams.holdingPeriodYears}
              onChange={handleParamChange}
              className="form-input"
              min="1"
              max="30"
            />
          </div>
        </div>
        
        <button 
          onClick={compareProperties} 
          className="btn btn-primary"
          disabled={!selectedProperty1Id || !selectedProperty2Id || loading}
        >
          {loading ? 'Comparing...' : 'Compare Properties'}
        </button>
      </div>
      
      {/* Error Message */}
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
          {error}
        </div>
      )}
      
      {/* Loading Indicator */}
      {loading && (
        <div className="flex justify-center items-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary"></div>
        </div>
      )}
      
      {/* Comparison Results */}
      {comparison && property1 && property2 && (
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <h2 className="text-2xl font-semibold mb-6">Comparison Results</h2>
          
          <div className="grid grid-cols-3 gap-4 mb-8">
            <div className="col-span-1"></div>
            <div className="col-span-1 text-center font-semibold">{property1.name}</div>
            <div className="col-span-1 text-center font-semibold">{property2.name}</div>
            
            {/* Basic Details */}
            <div className="col-span-3 bg-gray-100 p-2 font-semibold">Basic Details</div>
            
            <div>Location</div>
            <div className="text-center">{property1.location}</div>
            <div className="text-center">{property2.location}</div>
            
            <div>Price (AED)</div>
            <div className="text-center">{property1.price.toLocaleString()}</div>
            <div className="text-center">{property2.price.toLocaleString()}</div>
            
            <div>Size (sqft)</div>
            <div className="text-center">{property1.size}</div>
            <div className="text-center">{property2.size}</div>
            
            <div>Price per sqft (AED)</div>
            <div className="text-center">{Math.round(property1.price / property1.size).toLocaleString()}</div>
            <div className="text-center">{Math.round(property2.price / property2.size).toLocaleString()}</div>
            
            <div>Property Type</div>
            <div className="text-center">{property1.propertyType}</div>
            <div className="text-center">{property2.propertyType}</div>
            
            <div>Bedrooms</div>
            <div className="text-center">{property1.bedrooms}</div>
            <div className="text-center">{property2.bedrooms}</div>
            
            <div>Bathrooms</div>
            <div className="text-center">{property1.bathrooms}</div>
            <div className="text-center">{property2.bathrooms}</div>
            
            {/* Investment Analysis */}
            <div className="col-span-3 bg-gray-100 p-2 font-semibold mt-4">Investment Analysis</div>
            
            <div>Projected Value After {comparisonParams.holdingPeriodYears} Years (AED)</div>
            <div className="text-center">{comparison.property1ProjectedValue.toLocaleString()}</div>
            <div className="text-center">{comparison.property2ProjectedValue.toLocaleString()}</div>
            
            <div>Projected Appreciation (AED)</div>
            <div className="text-center">{(comparison.property1ProjectedValue - property1.price).toLocaleString()}</div>
            <div className="text-center">{(comparison.property2ProjectedValue - property2.price).toLocaleString()}</div>
            
            <div>Return on Investment (%)</div>
            <div className="text-center">{comparison.property1Roi.toFixed(2)}%</div>
            <div className="text-center">{comparison.property2Roi.toFixed(2)}%</div>
            
            <div>Annual Return (%)</div>
            <div className="text-center">{(comparison.property1Roi / comparisonParams.holdingPeriodYears).toFixed(2)}%</div>
            <div className="text-center">{(comparison.property2Roi / comparisonParams.holdingPeriodYears).toFixed(2)}%</div>
            
            {/* Recommendation */}
            <div className="col-span-3 bg-gray-100 p-2 font-semibold mt-4">Recommendation</div>
            
            <div className="col-span-3 p-4 bg-primary-light text-center">
              <p className="font-semibold mb-2">Based on your investment criteria:</p>
              <p>
                {comparison.property1Roi > comparison.property2Roi 
                  ? `${property1.name} offers a better return on investment with ${comparison.property1Roi.toFixed(2)}% ROI over ${comparisonParams.holdingPeriodYears} years.`
                  : comparison.property2Roi > comparison.property1Roi 
                    ? `${property2.name} offers a better return on investment with ${comparison.property2Roi.toFixed(2)}% ROI over ${comparisonParams.holdingPeriodYears} years.`
                    : 'Both properties offer the same return on investment.'}
              </p>
            </div>
          </div>
          
          <div className="flex justify-between">
            <Link to={`/properties/${property1.id}`} className="text-primary hover:underline">
              View {property1.name} Details
            </Link>
            <Link to={`/properties/${property2.id}`} className="text-primary hover:underline">
              View {property2.name} Details
            </Link>
          </div>
        </div>
      )}
      
      {/* Rent vs Buy Comparison */}
      {selectedProperty1Id && (
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold mb-4">Compare Renting vs Buying</h2>
          <p className="text-gray-600 mb-4">
            Want to compare renting versus buying for {property1?.name}?
          </p>
          <Link 
            to={`/rent-vs-buy?propertyId=${selectedProperty1Id}`} 
            className="btn btn-primary"
          >
            Rent vs Buy Analysis
          </Link>
        </div>
      )}
    </div>
  );
};

export default PropertyComparison;