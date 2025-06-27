import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

const PropertyDetail = () => {
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        setLoading(true);
        const response = await axios.get(`/api/properties/${id}`);
        setProperty(response.data);
        setError(null);
      } catch (err) {
        setError('Failed to fetch property details. Please try again later.');
        console.error('Error fetching property details:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchProperty();
  }, [id]);

  if (loading) {
    return (
      <div className="flex justify-center items-center py-20">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded my-6">
        <p>{error}</p>
        <Link to="/properties" className="text-primary hover:underline mt-2 inline-block">
          Back to Properties
        </Link>
      </div>
    );
  }

  if (!property) {
    return (
      <div className="text-center py-12">
        <h2 className="text-2xl font-semibold mb-4">Property Not Found</h2>
        <p className="text-gray-600 mb-6">The property you are looking for does not exist or has been removed.</p>
        <Link to="/properties" className="btn btn-primary">
          Back to Properties
        </Link>
      </div>
    );
  }

  return (
    <div>
      <div className="flex flex-wrap items-center justify-between mb-6">
        <h1 className="text-3xl font-bold">{property.name}</h1>
        <div className="flex space-x-3 mt-2 sm:mt-0">
          <Link to={`/property-comparison?property1Id=${property.id}`} className="btn btn-primary flex items-center">
            <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
            </svg>
            Compare
          </Link>
          <Link to={`/loan-calculator?propertyId=${property.id}`} className="btn btn-secondary flex items-center">
            <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            Calculate Loan
          </Link>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Main Content */}
        <div className="lg:col-span-2">
          {/* Property Image Placeholder */}
          <div className="bg-gray-200 h-96 rounded-lg flex items-center justify-center mb-8">
            <svg className="w-24 h-24 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"></path>
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 22V12h6v10"></path>
            </svg>
          </div>

          {/* Property Details */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-8">
            <h2 className="text-2xl font-semibold mb-4">Property Details</h2>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Location:</span> {property.location}
                </p>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Price:</span> AED {property.price.toLocaleString()}
                </p>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Size:</span> {property.size} sqft
                </p>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Property Type:</span> {property.propertyType}
                </p>
              </div>
              
              <div>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Bedrooms:</span> {property.bedrooms}
                </p>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Bathrooms:</span> {property.bathrooms}
                </p>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Community:</span> {property.communityName}
                </p>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Year Built:</span> {property.yearBuilt}
                </p>
                <p className="text-gray-600 mb-2">
                  <span className="font-semibold">Furnished:</span> {property.isFurnished ? 'Yes' : 'No'}
                </p>
              </div>
            </div>
          </div>

          {/* Property Description */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-8">
            <h2 className="text-2xl font-semibold mb-4">Description</h2>
            <p className="text-gray-600">
              This beautiful {property.propertyType.toLowerCase()} is located in {property.communityName}, {property.location}. 
              It features {property.bedrooms} bedrooms and {property.bathrooms} bathrooms with a total area of {property.size} sqft.
              {property.isFurnished && ' The property comes fully furnished and ready to move in.'}
              {property.yearBuilt && ` Built in ${property.yearBuilt}, this property offers modern amenities and design.`}
            </p>
          </div>
        </div>

        {/* Sidebar */}
        <div>
          {/* Price Card */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-8">
            <h3 className="text-xl font-semibold mb-4">Price Details</h3>
            <div className="text-3xl font-bold text-primary mb-4">
              AED {property.price.toLocaleString()}
            </div>
            <div className="text-gray-600 mb-4">
              AED {Math.round(property.price / property.size).toLocaleString()} per sqft
            </div>
            <Link to={`/loan-calculator?propertyId=${property.id}`} className="btn btn-primary w-full flex justify-center items-center">
              <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              Calculate Mortgage
            </Link>
          </div>

          {/* Additional Tools */}
          <div className="bg-white rounded-lg shadow-md p-6">
            <h3 className="text-xl font-semibold mb-4">Additional Tools</h3>
            <ul className="space-y-3">
              <li>
                <Link to={`/cost-breakdown?propertyId=${property.id}`} className="text-primary hover:underline flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z"></path>
                  </svg>
                  Cost Breakdown
                </Link>
              </li>
              <li>
                <Link to={`/service-charge-estimate?propertyId=${property.id}`} className="text-primary hover:underline flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17 9V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2m2 4h10a2 2 0 002-2v-6a2 2 0 00-2-2H9a2 2 0 00-2 2v6a2 2 0 002 2zm7-5a2 2 0 11-4 0 2 2 0 014 0z"></path>
                  </svg>
                  Service Charge Estimate
                </Link>
              </li>
              <li>
                <Link to={`/document-checklist?propertyId=${property.id}`} className="text-primary hover:underline flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4"></path>
                  </svg>
                  Document Checklist
                </Link>
              </li>
              <li>
                <Link to={`/property-comparison?property1Id=${property.id}`} className="text-primary hover:underline flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                  </svg>
                  Compare with Another Property
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PropertyDetail;