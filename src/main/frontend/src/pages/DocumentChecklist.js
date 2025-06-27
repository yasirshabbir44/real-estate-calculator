import React, { useState } from 'react';

function DocumentChecklist() {
  const [documents, setDocuments] = useState([
    { id: 1, name: 'Passport Copy', required: true, completed: false },
    { id: 2, name: 'Emirates ID', required: true, completed: false },
    { id: 3, name: 'Salary Certificate', required: true, completed: false },
    { id: 4, name: 'Bank Statements (6 months)', required: true, completed: false },
    { id: 5, name: 'Title Deed (for property owners)', required: false, completed: false },
    { id: 6, name: 'Mortgage Pre-Approval', required: false, completed: false },
    { id: 7, name: 'Property Valuation Report', required: false, completed: false },
    { id: 8, name: 'NOC from Developer', required: false, completed: false }
  ]);

  const toggleCompleted = (id) => {
    setDocuments(documents.map(doc => 
      doc.id === id ? { ...doc, completed: !doc.completed } : doc
    ));
  };

  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">Document Checklist</h1>
      <p className="mb-6">
        Use this checklist to ensure you have all the necessary documents for your real estate transaction.
        Required documents are marked with an asterisk (*).
      </p>
      
      <div className="bg-white shadow-md rounded-lg overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Document</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Required</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {documents.map((doc) => (
              <tr key={doc.id}>
                <td className="px-6 py-4 whitespace-nowrap">
                  <div className="text-sm font-medium text-gray-900">
                    {doc.name} {doc.required && <span className="text-red-500">*</span>}
                  </div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${doc.required ? 'bg-red-100 text-red-800' : 'bg-gray-100 text-gray-800'}`}>
                    {doc.required ? 'Required' : 'Optional'}
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <button
                    onClick={() => toggleCompleted(doc.id)}
                    className={`px-3 py-1 text-sm font-medium rounded-md ${
                      doc.completed 
                        ? 'bg-green-100 text-green-800 hover:bg-green-200' 
                        : 'bg-yellow-100 text-yellow-800 hover:bg-yellow-200'
                    }`}
                  >
                    {doc.completed ? 'Completed' : 'Pending'}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      
      <div className="mt-8 bg-blue-50 p-4 rounded-lg">
        <h2 className="text-lg font-semibold text-blue-800 mb-2">Need help with your documents?</h2>
        <p className="text-blue-700">
          Our team can assist you in preparing and verifying your documents. 
          Contact our document specialists at <a href="mailto:documents@realestate.com" className="underline">documents@realestate.com</a> or call <a href="tel:+97144001234" className="underline">+971 4 400 1234</a>.
        </p>
      </div>
    </div>
  );
}

export default DocumentChecklist;