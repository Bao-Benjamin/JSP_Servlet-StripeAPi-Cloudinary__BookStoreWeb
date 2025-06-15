package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stripe.model.BalanceTransaction;
import com.stripe.model.BalanceTransactionCollection;
import com.stripe.model.ChargeCollection;
import com.stripe.param.BalanceTransactionListParams;
import com.stripe.param.ChargeListParams;
import com.stripe.Stripe;
import com.stripe.model.Charge;

/**
 * Servlet implementation class doanhThu
 */
@WebServlet("/doanh-thu")
public class doanhThu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 private static final String STRIPE_SECRET_KEY = "your_stripe_api"; // Thay bằng API Key test của bạn
    public doanhThu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Khởi tạo Stripe API Key
       
        Stripe.apiKey = STRIPE_SECRET_KEY;

        try {
            // Lấy danh sách các giao dịch
            ChargeListParams params = ChargeListParams.builder()
                    .setLimit(100L) // Giới hạn 100 giao dịch gần nhất
                    .build();
            ChargeCollection charges = Charge.list(params);

            // Danh sách giao dịch
            List<String> transactions = new ArrayList();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            for (Charge charge : charges.getData()) {
                if (charge.getPaid() && charge.getCurrency().equals("vnd")) {
                    long amountInCents = charge.getAmount();  // Stripe trả về đơn vị cents
//                    long amountInVND = amountInCents / 100;   // Chuyển đổi sang VND
                    String formattedAmount = currencyFormat.format(amountInCents);

                    System.out.println("Mã giao dịch: " + charge.getId());
                    System.out.println("Số tiền (cent): " + charge.getAmount());
//                    System.out.println("Số tiền (VND): " + amountInVND);
                    System.out.println("Ngày giao dịch: " + new java.util.Date(charge.getCreated() * 1000));

                    String transaction = "Mã giao dịch: " + charge.getId() +
                                         " - Số tiền: " + formattedAmount +
                                         " - Ngày: " + new java.util.Date(charge.getCreated() * 1000);
                    transactions.add(transaction);
                }
            }

            // Gửi danh sách đến JSP
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("doanhthu/doanhthu.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy dữ liệu từ Stripe");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
