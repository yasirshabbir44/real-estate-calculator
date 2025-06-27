import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const PropertyList = () => {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({
    location: '',
    minPrice: '',
    maxPrice: '',
    propertyType: '',
    communityName: ''
  });

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = async () => {
    try {
      setLoading(true);
      const response = await axios.get('/api/properties');
      setProperties(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch properties. Please try again later.');
      console.error('Error fetching properties:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters({
      ...filters,
      [name]: value
    });
  };

  const applyFilters = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      let endpoint = '/api/properties';
      
      if (filters.location) {
        endpoint = `/api/properties/search/location?location=${encodeURIComponent(filters.location)}`;
      } else if (filters.minPrice && filters.maxPrice) {
        endpoint = `/api/properties/search/price?minPrice=${filters.minPrice}&maxPrice=${filters.maxPrice}`;
      } else if (filters.propertyType) {
        endpoint = `/api/properties/search/type?propertyType=${encodeURIComponent(filters.propertyType)}`;
      } else if (filters.communityName) {
        endpoint = `/api/properties/search/community?communityName=${encodeURIComponent(filters.communityName)}`;
      }
      
      const response = await axios.get(endpoint);
      setProperties(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to apply filters. Please try again later.');
      console.error('Error applying filters:', err);
    } finally {
      setLoading(false);
    }
  };

  const resetFilters = () => {
    setFilters({
      location: '',
      minPrice: '',
      maxPrice: '',
      propertyType: '',
      communityName: ''
    });
    fetchProperties();
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">Properties</h1>
      
      {/* Filters */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold mb-4">Filter Properties</h2>
        <form onSubmit={applyFilters}>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mb-4">
            <div>
              <label htmlFor="location" className="form-label">Location</label>
              <input
                type="text"
                id="location"
                name="location"
                value={filters.location}
                onChange={handleFilterChange}
                className="form-input"
                placeholder="e.g. Downtown Dubai"
              />
            </div>
            
            <div>
              <label htmlFor="minPrice" className="form-label">Min Price (AED)</label>
              <input
                type="number"
                id="minPrice"
                name="minPrice"
                value={filters.minPrice}
                onChange={handleFilterChange}
                className="form-input"
                placeholder="Minimum price"
                min="0"
              />
            </div>
            
            <div>
              <label htmlFor="maxPrice" className="form-label">Max Price (AED)</label>
              <input
                type="number"
                id="maxPrice"
                name="maxPrice"
                value={filters.maxPrice}
                onChange={handleFilterChange}
                className="form-input"
                placeholder="Maximum price"
                min="0"
              />
            </div>
            
            <div>
              <label htmlFor="propertyType" className="form-label">Property Type</label>
              <select
                id="propertyType"
                name="propertyType"
                value={filters.propertyType}
                onChange={handleFilterChange}
                className="form-input"
              >
                <option value="">All Types</option>
                <option value="Apartment">Apartment</option>
                <option value="Villa">Villa</option>
                <option value="Townhouse">Townhouse</option>
                <option value="Penthouse">Penthouse</option>
                <option value="Office">Office</option>
                <option value="Retail">Retail</option>
              </select>
            </div>
            
            <div>
              <label htmlFor="communityName" className="form-label">Community</label>
              <input
                type="text"
                id="communityName"
                name="communityName"
                value={filters.communityName}
                onChange={handleFilterChange}
                className="form-input"
                placeholder="e.g. Palm Jumeirah"
              />
            </div>
          </div>
          
          <div className="flex flex-wrap gap-3">
            <button type="submit" className="btn btn-primary">Apply Filters</button>
            <button type="button" onClick={resetFilters} className="btn btn-secondary">Reset Filters</button>
          </div>
        </form>
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
      
      {/* Property List */}
      {!loading && properties.length === 0 ? (
        <div className="text-center py-12">
          <p className="text-xl text-gray-600">No properties found matching your criteria.</p>
          <button onClick={resetFilters} className="mt-4 text-primary hover:underline">
            Reset filters and try again
          </button>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {properties.map(property => (
            <div key={property.id} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
              {/* Property Image Placeholder */}
              <div className="bg-gray-200 h-48 flex items-center justify-center">
                <svg className="w-16 h-16 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"></path>
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 22V12h6v10"></path>
                </svg>
              </div>
              
              <div className="p-6">
                <h3 className="text-xl font-semibold mb-2">{property.name}</h3>
                <p className="text-gray-600 mb-4">{property.location}</p>
                
                <div className="flex justify-between items-center mb-4">
                  <span className="text-2xl font-bold text-primary">AED {property.price.toLocaleString()}</span>
                  <span className="text-gray-600">{property.size} sqft</span>
                </div>
                
                <div className="flex justify-between text-gray-600 mb-6">
                  <span>{property.bedrooms} {property.bedrooms === 1 ? 'Bedroom' : 'Bedrooms'}</span>
                  <span>{property.bathrooms} {property.bathrooms === 1 ? 'Bathroom' : 'Bathrooms'}</span>
                  <span>{property.propertyType}</span>
                </div>
                
                <div className="flex justify-between">
                  <Link to={`/properties/${property.id}`} className="btn btn-primary">
                    View Details
                  </Link>
                  <Link to={`/property-comparison?property1Id=${property.id}`} className="text-primary hover:underline flex items-center">
                    <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                    </svg>
                    Compare
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default PropertyList;